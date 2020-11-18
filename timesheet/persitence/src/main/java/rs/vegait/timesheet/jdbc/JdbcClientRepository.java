package rs.vegait.timesheet.jdbc;

import org.springframework.stereotype.Component;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.client.Client;
import rs.vegait.timesheet.core.repository.ClientRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.UUID;

@Component
public class JdbcClientRepository implements ClientRepository {
    private final String TABLE_NAME = "clients";
    private final Connection connection;

    public JdbcClientRepository(Connection connection) throws SQLException {
        this.connection = connection;
    }


    @Override
    public void add(Client newObject) throws SQLException {
        String sql = "INSERT INTO "+TABLE_NAME+" (`id`, `name`, `countryName`, `cityName`, `postalCode`, `streetName`, `streetNumber`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        preparedStatement.setString(1,newObject.id().toString());
        preparedStatement.setString(2,newObject.name());
        preparedStatement.setString(3,newObject.address().country().name());
        preparedStatement.setString(4,newObject.address().city().name());
        preparedStatement.setInt(5,newObject.address().city().postalCode());
        preparedStatement.setString(6,newObject.address().street().name());
        preparedStatement.setString(7,newObject.address().street().number());
        preparedStatement.execute();
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

}
