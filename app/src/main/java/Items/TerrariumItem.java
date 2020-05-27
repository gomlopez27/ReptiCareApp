package Items;

import java.io.Serializable;
import java.util.List;

public class TerrariumItem implements Serializable {
    int id;
    String name, owner;
    Double min_temp, max_temp, min_humidity, max_humidity, min_uv, max_uv, current_temp, current_humidity, current_uv;
    List<String> otherusers;


    public TerrariumItem(){}

    public TerrariumItem(int id, String owner, String name, Double min_temp, Double max_temp, Double min_humidity, Double max_humidity, Double min_uv, Double max_uv, Double current_temp, Double current_humidity, Double current_uv, List<String> otherusers) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.min_humidity = min_humidity;
        this.max_humidity = max_humidity;
        this.min_uv = min_uv;
        this.max_uv = max_uv;
        this.current_temp = current_temp;
        this.current_humidity = current_humidity;
        this.current_uv = current_uv;
        this.otherusers = otherusers;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner(){
        return this.owner;
    }

    public void setOwner(String owner){
        this.owner = owner;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMin_temp() {
        return this.min_temp;
    }

    public void setMin_temp(Double min_temp) {
        this.min_temp = min_temp;
    }

    public Double getMax_temp() {
        return this.max_temp;
    }

    public void setMax_temp(Double max_temp) {
        this.max_temp = max_temp;
    }

    public Double getMin_humidity() {
        return this.min_humidity;
    }

    public void setMin_humidity(Double min_humidity) {
        this.min_humidity = min_humidity;
    }

    public Double getMax_humidity() {
        return this.max_humidity;
    }

    public void setMax_humidity(Double max_humidity) {
        this.max_humidity = max_humidity;
    }

    public Double getMin_uv() {
        return this.min_uv;
    }

    public void setMin_uv(Double min_uv) {
        this.min_uv = min_uv;
    }

    public Double getMax_uv() {
        return this.max_uv;
    }

    public void setMax_uv(Double max_uv) {
        this.max_uv = max_uv;
    }

    public Double getCurrent_temp() {
        return this.current_temp;
    }

    public void setCurrent_temp(Double current_temp) {
        this.current_temp = current_temp;
    }

    public Double getCurrent_humidity() {
        return this.current_humidity;
    }

    public void setCurrent_humidity(Double current_humidity) {
        this.current_humidity = current_humidity;
    }

    public Double getCurrent_uv() {
        return this.current_uv;
    }

    public void setCurrent_uv(Double current_uv) {
        this.current_uv = current_uv;
    }

    public List<String> getOtherusers() {
        return this.otherusers;
    }

    public void setOtherusers(List<String> otherusers) {
        this.otherusers = otherusers;
    }

}