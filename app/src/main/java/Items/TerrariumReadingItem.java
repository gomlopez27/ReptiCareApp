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

    public int getTerrariumId() {
        return terrariumId;
    }

    public void setTerrariumId(int terrariumId) {
        this.terrariumId = terrariumId;
    }

    public double getCurrTemp() {
        return currTemp;
    }

    public void setCurrTemp(double currTemp) {
        this.currTemp = currTemp;
    }

    public double getCurrHum() {
        return currHum;
    }

    public void setCurrHum(double currHum) {
        this.currHum = currHum;
    }

    public double getCurrUV() {
        return currUV;
    }

    public void setCurrUV(double currUV) {
        this.currUV = currUV;
    }

    public Boolean getActivity() {
        return activity;
    }

    public void setActivity(Boolean activity) {
        this.activity = activity;
    }

    public String getCreatingTime() {
        return creatingTime;
    }

    public void setCreatingTime(String creatingTime) {
        this.creatingTime = creatingTime;
    }
}
