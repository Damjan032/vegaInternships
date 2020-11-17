package models.client;

public class ClientName {
    private String name;

    public ClientName(String name) {
        if (name == null)
            throw new NullPointerException();
    }

    public String name() {
        return name;
    }
}
