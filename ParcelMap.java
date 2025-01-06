import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ParcelMap {
    private Map<String, Parcel> parcelMap;

    public ParcelMap() {
        parcelMap = new HashMap<>();
    }

    public void addParcel(Parcel parcel) {
        parcelMap.put(parcel.getParcelId(), parcel);
    }

    public Parcel findParcelById(String parcelId) {
        return parcelMap.get(parcelId);
    }

    public Collection<Parcel> getParcels() {
        return parcelMap.values();
    }

    // Method to remove a parcel after it has been collected
    public void removeParcel(String parcelId) {
        parcelMap.remove(parcelId);
    }
}
