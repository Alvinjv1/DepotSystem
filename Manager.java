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

    private JTextArea customerTextArea;
    private JTextArea parcelTextArea;
    private JTextArea logTextArea;
    private JButton processButton;

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

        parcelList = new JList<>(new Vector<>(parcelMap.getParcels())); // JList initialization
        JScrollPane parcelListScrollPane = new JScrollPane(parcelList);

        // Add components to panel
        panel.add(customerScrollPane, BorderLayout.NORTH);
        panel.add(parcelListScrollPane, BorderLayout.CENTER); // Add parcelList to the center
        panel.add(logScrollPane, BorderLayout.SOUTH);

        processButton = new JButton("Process Next Customer");
        processButton.addActionListener(e -> processNextCustomer());

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.getContentPane().add(processButton, BorderLayout.SOUTH);

        frame.setVisible(true);
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
            queue.removeCustomer();  // Remove the customer from the queue once processed

            Parcel parcel = parcelMap.findParcelById(customer.getParcelId());
            if (parcel != null) {
                double fee = worker.calculateFee(parcel);  // Worker calculates the fee
                log.addLog("Customer " + customer.getName() + " collected parcel " + parcel.getParcelId() + " with fee " + fee);

                // Update GUI with processed customer, parcel, and log
                customerTextArea.append("Customer: " + customer.getName() + "\n");
                parcelTextArea.append("Parcel: " + parcel.getParcelId() + ", Fee: " + fee + "\n");
                logTextArea.append("Log: " + log.getLogEntries().get(log.getLogEntries().size() - 1) + "\n");

                // Refresh the parcel list in the GUI
                parcelList.setListData(new Vector<>(parcelMap.getParcels()));
            } else {
                JOptionPane.showMessageDialog(null, "Parcel not found for customer: " + customer.getName(), "Error", JOptionPane.ERROR_MESSAGE);
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
