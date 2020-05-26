package Items;

import java.io.Serializable;

public class IssueItem implements Serializable {
    int id;
    String name;
    String desc;
    Boolean isResolved;

    public IssueItem(){}

    public IssueItem(String name, Boolean isResolved, String desc, int id) {
        this.name = name;
        this.isResolved = isResolved;
        this.desc = desc;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getResolved() {
        return isResolved;
    }

    public void setResolved(Boolean isResolved) {
        this.isResolved = isResolved;
    }

    public String getDesc(){
        return  desc;
    }

    public int getId(){
        return id;
    }
}
