
import model.Page;
import model.client.Client;
import repository.ClientRepository;

import java.util.Optional;
import java.util.UUID;

public class JdbcClientRepository implements ClientRepository {


    @Override
    public void add(Client newObject) {

    }

    @Override
    public void remove(UUID id) {

    }

    @Override
    public void update(Client newObject) {

    }

    @Override
    public Iterable<Client> findAll() {
        return null;
    }

    @Override
    public Optional<Client> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<Client> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Page<Client> findBy(String searchText, char firstLetter, int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public void removeByName(String id) {

    }


}

    /*private final Connection connection;

    public JdbcClientRepository(Connection connection) {
        this.connection = connection;
    }

    public void add(Client client) {

    }

    public void remove(UUID id) {

    }

    public void update(Client client) {

    }

    public Optional<Client> findById(UUID id) {
        return Optional.empty();
    }

    public Optional<Client> findByName(ClientName name) {
        return Optional.empty();
    }

    public Iterable<Client> findAll() {
        return null;
    }
}*/
