package rs.vegait.timesheet.core.model.client;

import java.util.UUID;

public class Client {
    private final UUID id;
    private final ClientName clientName;
    private final Address address;

    public Client(UUID id, ClientName clientName, Address address) {
        if (id == null || clientName == null || address == null)
            throw new IllegalArgumentException("Client params cant be null");
        this.id = id;
        this.clientName = clientName;
        this.address = address;
    }

    public Client changeName(ClientName newClientName) {
        return new Client(this.id, newClientName, this.address);
    }

    public Client changeAddress(Address newAddress) {
        return new Client(this.id, this.clientName, newAddress);
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return clientName.name();
    }

    public Address address() {
        return this.address;
    }
}
