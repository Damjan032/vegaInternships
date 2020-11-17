package models.client;

public class ClientAddress {
    private String address;

    public ClientAddress(String address) {
        if (address == null)
            throw new ExceptionInInitializerError();
    }

    public String address() {
        return address;
    }
}
