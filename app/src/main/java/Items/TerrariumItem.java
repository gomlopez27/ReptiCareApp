package Items;

import java.io.Serializable;
import java.util.List;

public class TerrariumItem implements Serializable {
    String name;
    Double min_temp;
    Double max_temp;
    Double min_humidity;
    Double max_humidity;
    Double min_uv;
    Double max_uv;
    Double current_temp;
    Double current_humidity;
    Double current_uv;
    List<String> otherusers;
    int id;


    public TerrariumItem(){}

    public TerrariumItem(String name){
        this.name = name;
    }

    public TerrariumItem(int id ,String name, Double min_temp, Double max_temp, Double min_humidity, Double max_humidity, Double min_uv, Double max_uv, Double current_temp, Double current_humidity, Double current_uv, List<String> otherusers) {
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
        this.id = id;
    }

    public Double getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(Double min_temp) {
        this.min_temp = min_temp;
    }

    public Double getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(Double max_temp) {
        this.max_temp = max_temp;
    }

    public Double getMin_humidity() {
        return min_humidity;
    }

    public void setMin_humidity(Double min_humidity) {
        this.min_humidity = min_humidity;
    }

    public Double getMax_humidity() {
        return max_humidity;
    }

    public void setMax_humidity(Double max_humidity) {
        this.max_humidity = max_humidity;
    }

    public Double getMin_uv() {
        return min_uv;
    }

    public void setMin_uv(Double min_uv) {
        this.min_uv = min_uv;
    }

    public Double getMax_uv() {
        return max_uv;
    }

    public void setMax_uv(Double max_uv) {
        this.max_uv = max_uv;
    }

    public Double getCurrent_temp() {
        return current_temp;
    }

    public void setCurrent_temp(Double current_temp) {
        this.current_temp = current_temp;
    }

    public Double getCurrent_humidity() {
        return current_humidity;
    }

    public void setCurrent_humidity(Double current_humidity) {
        this.current_humidity = current_humidity;
    }

    public Double getCurrent_uv() {
        return current_uv;
    }

    public void setCurrent_uv(Double current_uv) {
        this.current_uv = current_uv;
    }

    public List<String> getOtherusers() {
        return otherusers;
    }

    public void setOtherusers(List<String> otherusers) {
        this.otherusers = otherusers;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId(){return id;}

    public void setId(int id) {
        this.id = id;
    }
}
