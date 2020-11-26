package rs.vegait.timesheet.core.service;


import org.springframework.stereotype.Component;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.client.Client;
import rs.vegait.timesheet.core.model.client.Country;
import rs.vegait.timesheet.core.repository.ClientRepository;
import rs.vegait.timesheet.core.repository.ProjectRepository;

import javax.management.InstanceAlreadyExistsException;
import java.security.InvalidKeyException;
import java.util.UUID;

@Component
public class ClientService implements BaseService<Client, UUID> {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Iterable<Client> findAll() throws Exception {
        return this.clientRepository.findAll();
    }

    @Override
    public void create(Client client) throws Exception {
        var foundClient = this.clientRepository.findById(client.id());
        if (foundClient.isPresent()) {
            throw new InstanceAlreadyExistsException("Already exists with same id");
        }

        this.clientRepository.add(client);
    }

    @Override
    public void update(Client updateObject) throws Exception {
        var foundClient = this.clientRepository.findById(updateObject.id());
        if (!foundClient.isPresent()) {
            throw new InstanceAlreadyExistsException("Already exists with same id");
        }
        this.clientRepository.update(updateObject);

    }

    @Override
    public void delete(UUID id) throws Exception {
        var foundClient = this.clientRepository.findById(id);
        if (!foundClient.isPresent()) {
            throw new InvalidKeyException("Non-existent category");
        }
        this.clientRepository.remove(id);
    }


    public Client findById(String id) throws Exception {
        var foundClient = this.clientRepository.findById(UUID.fromString(id));
        if (!foundClient.isPresent()) {
            throw new InvalidKeyException("Non-existent employee");
        }
        return foundClient.get();
    }

    public Page<Client> search(int pageNumber, int pageSize, String searchString, Character firstLetter) throws Exception {
        return this.clientRepository.findBy(searchString == null ? " " : searchString, firstLetter == null ? ' ' : firstLetter, pageNumber, pageSize);
    }
}
