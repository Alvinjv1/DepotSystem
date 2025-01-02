import java.util.List;

public class Manager {
    private QueofCustomers customerQueue;
    private ParcelMap parcelMap;

    public Manager() {
        this.customerQueue = new QueofCustomers();
        this.parcelMap = new ParcelMap();
    }

    public void loadParcels(List<Parcel> parcels) {
        for (Parcel parcel : parcels) {
            parcelMap.addParcel(parcel);
        }
    }

    public void loadCustomers(List<Customer> customers) {
        for (Customer customer : customers) {
            customerQueue.addCustomer(customer);
        }
    }

    public void processAllCustomers() {
        Worker worker = new Worker(parcelMap);
        while (!customerQueue.isEmpty()) {
            Customer currentCustomer = customerQueue.processNextCustomer();
            worker.processCustomer(currentCustomer);
        }
    }

    public void generateLogFile(String filename) {
        Log.getInstance().writeToFile(filename);
    }
}
