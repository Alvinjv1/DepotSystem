import java.util.HashMap;
import java.util.Map;

public class ParcelMap {
    private Map<String, Parcel> parcels;

    public ParcelMap() {
        this.parcels = new HashMap<>();
    }

    public void addParcel(Parcel parcel) {
        parcels.put(parcel.getId(), parcel);
    }

    public Parcel findParcelById(String parcelId) {
        return parcels.get(parcelId);
    }

    public void removeParcel(String parcelId) {
        parcels.remove(parcelId);
    }

    public Map<String, Parcel> getParcels() {
        return parcels;
    }
}
