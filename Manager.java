import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Queue;
import java.util.Vector;

public class Manager {
    private QueueOfCustomers queue;
    private ParcelMap parcelMap;
    private Log log;
    private Worker worker;
    private JList<Parcel> parcelList; // JList to display parcels
    private JList<Customer> customerList; // JList to display customers in the queue

    private JTextArea customerTextArea;
    private JTextArea parcelTextArea;
    private JTextArea logTextArea;
    private JButton processButton;
    private JButton addParcelButton;  // Button to add new parcel
    private JButton manualParcelButton; // Button for manual parcel ID entry

    public Manager() {
        queue = new QueueOfCustomers();
        parcelMap = new ParcelMap();
        log = Log.getInstance();  // Singleton instance for logging
        worker = new Worker(queue, parcelMap); // Initialize the Worker

        // Initialize GUI components
        initializeGUI();
    }

    public void initializeGUI() {
        JFrame frame = new JFrame("Depot System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600); // Adjusted size for better visibility

        // Create panels
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        customerTextArea = new JTextArea(10, 30);
        parcelTextArea = new JTextArea(10, 30);
        logTextArea = new JTextArea(10, 30);

        JScrollPane customerScrollPane = new JScrollPane(customerTextArea);
        JScrollPane parcelScrollPane = new JScrollPane(parcelTextArea);
        JScrollPane logScrollPane = new JScrollPane(logTextArea);

        // JList to display parcels
        parcelList = new JList<>(new Vector<>(parcelMap.getParcels()));
        JScrollPane parcelListScrollPane = new JScrollPane(parcelList);

        // JList to display customers in the queue
        customerList = new JList<>(new Vector<>(queue.getCustomers()));
        JScrollPane customerListScrollPane = new JScrollPane(customerList);

        // Add components to panel
        panel.add(customerScrollPane, BorderLayout.NORTH);
        panel.add(customerListScrollPane, BorderLayout.WEST); // Add customerList to the left side
        panel.add(parcelListScrollPane, BorderLayout.CENTER); // Add parcelList to the center
        panel.add(logScrollPane, BorderLayout.SOUTH);

        processButton = new JButton("Process Next Customer");
        processButton.addActionListener(e -> processNextCustomer());

        addParcelButton = new JButton("Add New Parcel");
        addParcelButton.addActionListener(e -> showAddParcelDialog());  // Action for adding a new parcel

        manualParcelButton = new JButton("Manual Parcel ID");
        manualParcelButton.addActionListener(e -> showManualParcelIdDialog()); // Action for manual parcel ID entry

        // Add buttons to the frame
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(processButton);
        buttonPanel.add(addParcelButton);
        buttonPanel.add(manualParcelButton);  // Add manual parcel button

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Method to show dialog for adding a new parcel
    private void showAddParcelDialog() {
        // Show dialog to enter customer and parcel information
        JTextField nameField = new JTextField(20);
        JTextField parcelIdField = new JTextField(20);
        JTextField lengthField = new JTextField(5);
        JTextField widthField = new JTextField(5);
        JTextField heightField = new JTextField(5);
        JTextField weightField = new JTextField(5);
        JTextField daysInDepotField = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel("Customer Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Parcel ID:"));
        panel.add(parcelIdField);
        panel.add(new JLabel("Length:"));
        panel.add(lengthField);
        panel.add(new JLabel("Width:"));
        panel.add(widthField);
        panel.add(new JLabel("Height:"));
        panel.add(heightField);
        panel.add(new JLabel("Weight:"));
        panel.add(weightField);
        panel.add(new JLabel("Days in Depot:"));
        panel.add(daysInDepotField);

        int option = JOptionPane.showConfirmDialog(null, panel, "Add New Parcel", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            // Collect entered data and create new customer and parcel
            String customerName = nameField.getText().trim();
            String parcelId = parcelIdField.getText().trim();
            int length = Integer.parseInt(lengthField.getText().trim());
            int width = Integer.parseInt(widthField.getText().trim());
            int height = Integer.parseInt(heightField.getText().trim());
            double weight = Double.parseDouble(weightField.getText().trim());
            int daysInDepot = Integer.parseInt(daysInDepotField.getText().trim());

            // Generate new customer and parcel objects
            Customer customer = new Customer(queue.getCustomers().size() + 1, customerName, parcelId);  // Assuming a unique customer ID is generated based on queue size
            Parcel parcel = new Parcel(parcelId, length, width, height, weight, daysInDepot);

            // Add the new customer and parcel to their respective collections
            queue.addCustomer(customer);
            parcelMap.addParcel(parcel);

            // Update the GUI (refresh the customer list and parcel list)
            customerList.setListData(new Vector<>(queue.getCustomers()));
            parcelList.setListData(new Vector<>(parcelMap.getParcels()));

            // Optionally, display added parcel and customer info in the text areas
            customerTextArea.append("Customer Added: " + customerName + "\n");
            parcelTextArea.append("Parcel Added: " + parcelId + ", Dimensions: " + length + "x" + width + "x" + height + ", Weight: " + weight + "\n");
        }
    }

    // Method to show dialog for manual parcel ID entry
    private void showManualParcelIdDialog() {
        JTextField parcelIdField = new JTextField(20);
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JLabel("Enter Parcel ID:"));
        panel.add(parcelIdField);

        int option = JOptionPane.showConfirmDialog(null, panel, "Manual Parcel ID Entry", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String enteredParcelId = parcelIdField.getText().trim();

            // Find the customer in the queue and process manually
            Customer customer = queue.get();  // Get the first customer in the queue
            Parcel parcel = parcelMap.findParcelById(enteredParcelId);

            if (parcel != null) {
                // Process the parcel and calculate the fee
                double fee = worker.calculateFee(parcel);
                log.addLog("Customer " + customer.getName() + " collected parcel " + parcel.getParcelId() + " with fee " + fee);

                // Update GUI with processed customer, parcel, and log
                customerTextArea.append("Customer: " + customer.getName() + " (Parcel ID: " + parcel.getParcelId() + ")\n");
                parcelTextArea.append("Parcel: " + parcel.getParcelId() + ", Fee: " + fee + "\n");
                logTextArea.append("Log: " + log.getLogEntries().get(log.getLogEntries().size() - 1) + "\n");

                // Refresh the customer list and parcel list in the GUI
                customerList.setListData(new Vector<>(queue.getCustomers()));
                parcelList.setListData(new Vector<>(parcelMap.getParcels()));

                // Remove the customer from the queue after processing
                queue.removeCustomer();

                // Remove the parcel from the parcel map
                parcelMap.removeParcel(parcel.getParcelId());
            } else {
                JOptionPane.showMessageDialog(null, "Parcel not found for the entered ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void loadCustomerData(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int seqNo = Integer.parseInt(parts[0]);
                String name = parts[1] + " " + parts[2];
                String parcelId = parts[3];
                queue.addCustomer(new Customer(seqNo, name, parcelId));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading customer data from file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadParcelData(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String parcelId = parts[0];
                int length = Integer.parseInt(parts[1]);
                int width = Integer.parseInt(parts[2]);
                int height = Integer.parseInt(parts[3]);
                int weight = Integer.parseInt(parts[4]);
                int daysInDepot = Integer.parseInt(parts[5]);

                Parcel parcel = new Parcel(parcelId, length, width, height, weight, daysInDepot);
                parcelMap.addParcel(parcel);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading parcel data from file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void processNextCustomer() {
        if (!queue.getCustomers().isEmpty()) {
            Customer customer = queue.get();  // Get the first customer in the queue

            // Go through the entire parcel list to find the matching parcel
            Parcel matchingParcel = null;
            for (Parcel parcel : parcelMap.getParcels()) {
                if (parcel.getParcelId().equals(customer.getParcelId())) {
                    matchingParcel = parcel;
                    break;
                }
            }

            // If parcel is found, process it
            if (matchingParcel != null) {
                double fee = worker.calculateFee(matchingParcel);  // Worker calculates the fee
                log.addLog("Customer " + customer.getName() + " collected parcel " + matchingParcel.getParcelId() + " with fee " + fee);

                // Update GUI with processed customer, parcel, and log
                customerTextArea.append("Customer: " + customer.getName() + " (Parcel ID: " + matchingParcel.getParcelId() + ")\n");
                parcelTextArea.append("Parcel: " + matchingParcel.getParcelId() + ", Fee: " + fee + "\n");
                logTextArea.append("Log: " + log.getLogEntries().get(log.getLogEntries().size() - 1) + "\n");

                // Refresh the customer list and parcel list in the GUI
                customerList.setListData(new Vector<>(queue.getCustomers()));
                parcelList.setListData(new Vector<>(parcelMap.getParcels()));

                // Remove the customer from the queue after processing
                queue.removeCustomer();

                // Remove the processed parcel from the list
                parcelMap.removeParcel(matchingParcel.getParcelId());
            } else {
                // If parcel not found, show error and leave the customer in the queue
                JOptionPane.showMessageDialog(null, "Parcel not found for customer: " + customer.getName(), "Error", JOptionPane.ERROR_MESSAGE);

                // Log that the customer remains in the queue
                log.addLog("Customer " + customer.getName() + " could not be processed because the parcel was not found. They remain in the queue.");

                // Update the customer list in the GUI
                customerList.setListData(new Vector<>(queue.getCustomers()));
            }
        } else {
            JOptionPane.showMessageDialog(null, "No customers in the queue to process.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public QueueOfCustomers getCustomerQueue() {
        return queue;
    }

    public static void main(String[] args) {
        Manager manager = new Manager();

        // Load customer and parcel data from files (make sure paths are correct)
        manager.loadCustomerData("C:\\Users\\Alvin\\OneDrive - University of Hertfordshire\\year3\\Alvin_21070011\\data\\customerData.txt");
        manager.loadParcelData("C:\\Users\\Alvin\\OneDrive - University of Hertfordshire\\year3\\Alvin_21070011\\data\\parcelData.txt");
    }
}
