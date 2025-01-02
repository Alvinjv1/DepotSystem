public class Worker {
    private ParcelMap parcelMap;

    public Worker(ParcelMap parcelMap) {
        this.parcelMap = parcelMap;
    }

    public double calculateFee(Parcel parcel) {
        double fee = parcel.getWeight() * 2; // Base fee based on weight
        fee += parcel.getVolume() * 0.5;    // Additional fee based on volume
        fee += parcel.getDaysInDepot() * 1; // Surcharge for days in depot
        return fee;
    }

    public void processCustomer(Customer customer) {
        Parcel parcel = parcelMap.findParcelById(customer.getParcelId());
        if (parcel == null || parcel.getStatus()) {
            Log.getInstance().addEvent("Parcel ID " + customer.getParcelId() + " not found or already collected for customer: " + customer.getCustomerName());
            return;
        }

        double fee = calculateFee(parcel);
        Log.getInstance().addEvent("Processing customer: " + customer.getCustomerName() +
                ", Parcel ID: " + parcel.getId() +
                ", Fee: $" + fee);
        parcel.setStatus(   true); // Update parcel status
    }
}
