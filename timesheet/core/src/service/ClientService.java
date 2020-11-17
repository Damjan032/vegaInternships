package service;

import model.client.Client;
import repository.ClientRepository;

import java.util.Optional;
import java.util.UUID;

public class ClientService implements IService<Client, UUID> {

    private ClientRepository clientRepository;
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void create(Client client) {
        var foundClient = this.clientRepository.findById(client.id());
        if (foundClient.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }

        this.clientRepository.add(client);
    }

    @Override
    public Optional<Client> readById(UUID id) {
        return this.clientRepository.findById(id);
    }

    @Override
    public void update(Client updateObject) {
        var foundClient = this.clientRepository.findById(updateObject.id());
        if (foundClient.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }
        this.clientRepository.update(updateObject);

    }

    @Override
    public void delete(UUID id) {
        var foundClient = this.clientRepository.findById(id);
        if (!foundClient.isPresent()) {
            throw new RuntimeException("Non-existent client");
        }
        this.clientRepository.remove(id);
    }


}
