public class Parcel {
    private String Id;
    private int Height;
    private double Weight;
    private int Length;
    private int Width;
    private Boolean Status;
    private int daysInDepot;
    public Parcel(String Id , int daysInDepot, Double Weigh ,int High , int Len,int Width ){
        this.Id = Id;
        Height = High;
        this.Width = Width;
        Weight = Weigh;
        Length = Len;
        this.Status = false;//default
        this.daysInDepot = daysInDepot;






    }
    public int getDaysInDepot (){
        return  daysInDepot;
    }
    public void setDaysInDepot(int days){
        this.daysInDepot = days;
    }
    public int getLength (){
        return  Length;
    }
    public Double getWeight (){
        return  Weight;
    }
    public int getWidth (){
        return  Width;
    }
    public Boolean getStatus (){
        return  Status;
    }
    public void setStatus (Boolean collected){
        this.Status= collected;
    }
    public int getVolume (){
        return  Height*Length*Width ;

    }
    public int getHeight (){
        return  Height;
    }

    public String getId() {
        return Id;
    }
    public String toString(){
        return "Parcel ID: "+Id + "\nDays in Depot: " +daysInDepot+ "\nWeight: "+Weight+"kg\nDimensions: " +
                Length+"x"+Width+"x"+Height+"\nCollected: "+(Status? "Yes":"No");
    }
}
