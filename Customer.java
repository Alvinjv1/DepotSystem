public class Customer {
    private int seqId;
    private String customerName;
    private String parcelId;
    //private String seqId;



public Customer(int seqId , String name , String pId){
    this.seqId = seqId;
    customerName = name;
    parcelId =pId;
}

    public String getCustomerName() {
        return customerName;
    }

    public String getParcelId() {
        return parcelId;
    }

    public int getSeqId() {
        return seqId;
    }
    @Override
    public String toString() {
        return "Customer SeqNo: " + seqId + ", Name: " + customerName + ", Parcel ID: " + parcelId;
    }
}
