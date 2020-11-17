package repository;

import jdk.jfr.Category;
import models.Page;
import models.client.Client;
import models.client.ClientName;

import java.util.Optional;
import java.util.UUID;

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
