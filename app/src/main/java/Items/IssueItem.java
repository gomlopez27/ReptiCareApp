package Items;

public class IssueItem {
    String name;
    Boolean isResolved;

    public IssueItem(){}

    public IssueItem(String name, Boolean isResolved) {
        this.name = name;
        this.isResolved = isResolved;
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

}
