package Items;

import java.io.Serializable;

public class IssueItem implements Serializable {
    int id;
    String name, desc;
    Boolean isResolved;


    public IssueItem(){}

    public IssueItem(String name, Boolean isResolved, String desc, int id) {
        this.name = name;
        this.isResolved = isResolved;
        this.desc = desc;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getResolved() {
        return this.isResolved;
    }

    public void setResolved(Boolean resolved) {
        isResolved = resolved;
    }

    public String getDesc(){
        return  this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId(){
        return this.id;
    }

    public int setId(int id){
        return this.id = id;
    }
}