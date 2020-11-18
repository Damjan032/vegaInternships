package rs.vegait.timesheet.jdbc;

import org.springframework.stereotype.Component;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.client.Client;
import rs.vegait.timesheet.core.repository.ClientRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.UUID;

@Component
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
    public void add(Client newObject) throws SQLException {

    }

    @Override
    public void remove(UUID id) throws SQLException {

    }

    @Override
    public void update(Client newObject) {

    }

    @Override
    public Iterable<Client> findAll() throws SQLException {
        return null;
    }

    @Override
    public Optional<Client> findById(UUID id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Client> findByName(String name) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Page<Client> findBy(String searchText, char firstLetter, int pageNumber, int pageSize) throws SQLException {
        return null;
    }

    @Override
    public void removeByName(String id) throws SQLException {

    }
}
