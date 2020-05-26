package Items;

import java.io.Serializable;

public class TerrariumReadingItem implements Serializable {
    int id;
    int terrariumId;
    double currTemp;
    double currHum;
    double currUV;
    Boolean activity;
    String creatingTime;

    public TerrariumReadingItem(){}

    public TerrariumReadingItem(int id, int terrariumId, double currTemp, double currHum, double currUV, Boolean activity, String creatingTime) {
        this.id = id;
        this.terrariumId = terrariumId;
        this.currTemp = currTemp;
        this.currHum = currHum;
        this.currUV = currUV;
        this.activity = activity;
        this.creatingTime = creatingTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
