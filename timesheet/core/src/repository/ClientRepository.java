package repository;
import model.client.Client;

// new module persistence
// JDBC

// CQS command/query
public interface ClientRepository extends CoreRepository<Client> {
    /*void add(Client client);
    void remove(UUID id);
    void update(Client client);
    Optional<Client> findById(UUID id);
    Optional<Client> findByName(ClientName name);
    Iterable<Client> findAll();
    Page<Client> findBy(String searchText, char firstLetter, int pageNumber, int pageSize);*/
}
