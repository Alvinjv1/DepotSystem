public class Customer {
    private int seqNo;
    private String name;
    private String parcelId;

    public Customer(int seqNo, String name, String parcelId) {
        this.seqNo = seqNo;
        this.name = name;
        this.parcelId = parcelId;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public String getName() {
        return name;
    }

    public String getParcelId() {
        return parcelId;
    }

    @Override
    public String toString() {
        return seqNo + " " + name + " " + parcelId;
    }
}
