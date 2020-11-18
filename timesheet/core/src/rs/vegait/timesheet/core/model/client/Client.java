package rs.vegait.timesheet.core.model.client;

import java.util.UUID;

public class Client {
    private UUID id;
    private ClientName clientName;
    private Address address;

    public Client(UUID id, ClientName clientName, Address address) {
        if (id == null || clientName == null || address == null)
            throw new ExceptionInInitializerError();
        this.id = id;
        System.out.println(clientName.name());
        System.out.println(clientName);
        System.out.println("AADADADA");
        this.clientName = clientName;
        this.address = address;
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return clientName.name();
    }

    public Address address() { return this.address; }
}
