package rs.vegait.timesheet.core.model.client;

public class ClientName {
    private String name;

    public ClientName(String name) {
        if (name == null || name.trim().length()==0)
            throw new IllegalArgumentException("Client name is not in valid format");
        this.name = name;
    }

    public String name() {
        return name;
    }
}
