package rs.vegait.timesheet.core.model.client;

public class ClientName {
    private String name;

    public ClientName(String name) {
        if (name == null)
            throw new NullPointerException();
        this.name = name;
    }

    public String name() {
        return name;
    }
}
