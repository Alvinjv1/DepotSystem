public class Worker {
    private QueueOfCustomers queueOfCustomers;
    private ParcelMap parcelMap;

    public Worker(QueueOfCustomers queueOfCustomers, ParcelMap parcelMap) {
        this.queueOfCustomers = queueOfCustomers;
        this.parcelMap = parcelMap;
    }

    public void processCustomer() {
        Customer customer = queueOfCustomers.removeCustomer();
        if (customer != null) {
            Parcel parcel = parcelMap.findParcelById(customer.getParcelId());
            if (parcel != null) {
                double fee = calculateFee(parcel);
                Log.getInstance().addLog("Customer " + customer.getName() + " collected parcel " + parcel.getParcelId() + " with fee " + fee);

                // Remove the parcel from the map after it is collected
                parcelMap.removeParcel(parcel.getParcelId());
            } else {
                Log.getInstance().addLog("Parcel " + customer.getParcelId() + " not found.");
            }
        }
    }

    public double calculateFee(Parcel parcel) {
        // Basic fee calculation based on parcel attributes
        double fee = parcel.getWeight() * 0.5 + parcel.getLength() * parcel.getWidth() * 0.2 + parcel.getDaysInDepot() * 0.1;
        return fee;
    }
}
