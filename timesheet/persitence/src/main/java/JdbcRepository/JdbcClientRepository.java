package JdbcRepository;

import model.Page;
import model.client.Client;
import repository.ClientRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.UUID;

public class JdbcClientRepository implements ClientRepository {
    private final String TABLE_NAME = "clients";
    private Statement statement;

    public JdbcClientRepository(Connection connection) throws SQLException {
        statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (id VARCHAR(255) not NULL , " +
                " name VARCHAR(255) not NULL, " +
                " countryName VARCHAR(255) not NULL, " +
                " cityName VARCHAR(255) not NULL, " +
                " postalCode VARCHAR(255) not NULL, " +
                " streetName VARCHAR(255) not NULL, " +
                " number VARCHAR(255) not NULL, " +
                " PRIMARY KEY ( id ))");
    }

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
