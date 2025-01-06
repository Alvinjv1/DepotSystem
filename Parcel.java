public class Parcel {
    private String parcelId;
    private int length;
    private int width;
    private int height;
    private double weight;
    private int daysInDepot;

    public Parcel(String parcelId, int length, int width, int height, double weight, int daysInDepot) {
        this.parcelId = parcelId;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.daysInDepot = daysInDepot;
    }

    public String getParcelId() {
        return parcelId;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public int getDaysInDepot() {
        return daysInDepot;
    }

    @Override
    public String toString() {
        return "Parcel ID: " + parcelId + ", Dimensions: " + length + "x" + width + "x" + height + ", Weight: " + weight + ", Days in Depot: " + daysInDepot;
    }
}
