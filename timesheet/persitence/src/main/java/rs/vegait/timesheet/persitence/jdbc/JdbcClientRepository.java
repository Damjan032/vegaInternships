package rs.vegait.timesheet.persitence.jdbc;

import org.springframework.stereotype.Component;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.client.*;
import rs.vegait.timesheet.core.repository.ClientRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JdbcClientRepository implements ClientRepository {
    private final String TABLE_NAME = "clients";
    private final Connection connection;

    public JdbcClientRepository(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void add(Client newObject) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " (`id`, `name`, `countryName`, `cityName`, `postalCode`, `streetName`, `streetNumber`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        preparedStatement.setString(1, newObject.id().toString());
        preparedStatement.setString(2, newObject.name());
        preparedStatement.setString(3, newObject.address().country().name());
        preparedStatement.setString(4, newObject.address().city().name());
        preparedStatement.setInt(5, newObject.address().city().postalCode());
        preparedStatement.setString(6, newObject.address().street().name());
        preparedStatement.setString(7, newObject.address().street().number());
        preparedStatement.execute();
    }

    @Override
    public void remove(UUID id) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " " +
                " WHERE id like (?)";

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, id.toString());
        pstmt.execute();
    }

    @Override
    public void update(Client newObject) throws SQLException {
        Optional<Client> clientOptional = this.findById(newObject.id());
        if (!clientOptional.isPresent()) {
            return;
        }
        String sql = "UPDATE " + TABLE_NAME + " SET " +
                "name = ?, " +
                "countryName = ?, " +
                "cityName = ?, " +
                "postalCode = ?, " +
                "streetName = ?, " +
                "streetNumber = ? " +
                "WHERE (id = ?)";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        preparedStatement.setString(1,
                newObject.name() == null ? clientOptional.get().name() : newObject.name());
        preparedStatement.setString(2,
                newObject.address().country().name() == null ? clientOptional.get().address().country().name() : newObject.address().country().name());
        preparedStatement.setString(3,
                newObject.address().city().name() == null ? clientOptional.get().address().city().name() : newObject.address().city().name());
        preparedStatement.setInt(4,
                newObject.address().city().postalCode() <= 0 ? clientOptional.get().address().city().postalCode() : newObject.address().city().postalCode());
        preparedStatement.setString(5,
                newObject.address().street().name() == null ? clientOptional.get().address().street().name() : newObject.address().street().name());
        preparedStatement.setString(6,
                newObject.address().street().number() == null ? clientOptional.get().address().street().number() : newObject.address().street().number());
        preparedStatement.setString(7, clientOptional.get().id().toString());

        preparedStatement.executeUpdate();

    }

    @Override
    public Iterable<Client> findAll() throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY name";
        List<Client> clients = new ArrayList<>();

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            clients.add(new Client(UUID.fromString(rs.getString("id")), new ClientName(rs.getString("name")),
                    new Address(
                            new Street(rs.getString("streetName"), rs.getString("streetNumber")),
                            new City(rs.getString("cityName"), rs.getInt("postalCode")),
                            new Country(rs.getString("countryName"))
                    )));
        }
        rs.close();
        return clients;
    }

    @Override
    public Optional<Client> findById(UUID id) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id LIKE(?) ";
        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, id.toString());
        Client client = null;

        //Statement statement = connection.createStatement();
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            client = new Client(UUID.fromString(rs.getString("id")), new ClientName(rs.getString("name")),
                    new Address(
                            new Street(rs.getString("streetName"), rs.getString("streetNumber")),
                            new City(rs.getString("cityName"), rs.getInt("postalCode")),
                            new Country(rs.getString("countryName"))
                    ));
        }
        rs.close();

        if (client == null) {
            return Optional.empty();
        }
        return Optional.of(client);
    }

    @Override
    public Optional<Client> findByName(String name) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Page<Client> findBy(String searchText, char firstLetter, int pageNumber, int pageSize) throws SQLException {
        String sqlSelect = "SELECT * FROM " + TABLE_NAME;

        String whereSql2 = " WHERE  " +
                "(UPPER(name) LIKE UPPER('" + firstLetter + "%') OR " +//name
                "UPPER(countryName) LIKE UPPER('" + firstLetter + "%') OR " + //countryName
                "UPPER(cityName) LIKE UPPER('" + firstLetter + "%') OR " + //cityName
                "UPPER(streetName) LIKE UPPER('" + firstLetter + "%') OR " + //streetName
                "UPPER(streetNumber) LIKE UPPER('" + firstLetter + "%')  " + //streetNumber
                ")";
        String whereSql3 = " WHERE  " +
                "(UPPER(name) LIKE UPPER('%" + searchText + "%') OR " +//name
                "UPPER(countryName) LIKE UPPER('%" + searchText + "%') OR " + //countryName
                "UPPER(cityName) LIKE UPPER('%" + searchText + "%') OR " + //cityName
                "UPPER(streetName) LIKE UPPER('%" + searchText + "%') OR " + //streetName
                "UPPER(streetNumber) LIKE UPPER('%" + searchText + "%') OR " + //streetNumber
                "UPPER(CONVERT(postalCode, CHAR)) LIKE UPPER('%" + searchText + "%')  " + //postalCode
                ")";
        String whereSql4 = " WHERE ( " +
                "(UPPER(name) LIKE UPPER('%" + searchText + "%') OR " +//name
                "UPPER(countryName) LIKE UPPER('%" + searchText + "%') OR " + //countryName
                "UPPER(cityName) LIKE UPPER('%" + searchText + "%') OR " + //cityName
                "UPPER(streetName) LIKE UPPER('%" + searchText + "%') OR " + //streetName
                "UPPER(streetNumber) LIKE UPPER('%" + searchText + "%') OR " + //streetNumber
                "UPPER(CONVERT(postalCode, CHAR)) LIKE UPPER('%" + searchText + "%')) AND " + //postalCode

                "(UPPER(name) LIKE UPPER('" + firstLetter + "%') OR " +//name
                "UPPER(countryName) LIKE UPPER('" + firstLetter + "%') OR " + //countryName
                "UPPER(cityName) LIKE UPPER('" + firstLetter + "%') OR " + //cityName
                "UPPER(streetName) LIKE UPPER('" + firstLetter + "%') OR " + //streetName
                "UPPER(streetNumber) LIKE UPPER('" + firstLetter + "%') " + //streetNumber
                ") )";
        String sqlLimitPart = " ORDER BY name LIMIT  ?, ?";
        PreparedStatement preparedStatement = null;
        boolean options1 = (searchText.trim().equalsIgnoreCase("") || searchText == null) && (firstLetter == ' '); //Nothing
        boolean options2 = (searchText.trim().equalsIgnoreCase("") || searchText == null) && (firstLetter != ' '); //Only firstLetter
        boolean options3 = (!searchText.trim().equalsIgnoreCase("") && searchText != null) && (firstLetter == ' '); //Only search
        boolean options4 = (!searchText.trim().equalsIgnoreCase("") && searchText != null) && (firstLetter != ' '); //Search and firstLetter
        String selectCount = "SELECT COUNT(*) AS total FROM " + TABLE_NAME;
        if (options1) {
            preparedStatement = connection.prepareStatement(sqlSelect + sqlLimitPart);
        } else if (options2) {
            preparedStatement = connection.prepareStatement(sqlSelect + whereSql2 + sqlLimitPart);
            selectCount = selectCount + whereSql2;
        } else if (options3) {
            preparedStatement = connection.prepareStatement(sqlSelect + whereSql3 + sqlLimitPart);
            selectCount = selectCount + whereSql3;
        } else {
            preparedStatement = connection.prepareStatement(sqlSelect + whereSql4 + sqlLimitPart);
            selectCount = selectCount + whereSql4;
        }

        List<Client> clients = new ArrayList<>();
        int numberOfRows = 0;
        preparedStatement.setInt(1, (pageNumber - 1) * pageSize);
        preparedStatement.setInt(2, Math.min(Math.abs(numberOfRows - pageSize * pageNumber), pageSize));
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(selectCount);
        rs.next();
        numberOfRows = rs.getInt("total");


        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            clients.add(new Client(UUID.fromString(rs.getString("id")), new ClientName(rs.getString("name")),
                    new Address(
                            new Street(rs.getString("streetName"), rs.getString("streetNumber")),
                            new City(rs.getString("cityName"), rs.getInt("postalCode")),
                            new Country(rs.getString("countryName"))
                    )));
        }
        rs.close();

        Page<Client> clientPage = new Page<Client>(clients, pageNumber, pageSize, numberOfRows);
        return clientPage;
    }

    public Iterable<Country> findAllCountries() throws SQLException {
        String sql = "SELECT * FROM countries";
        List<Country> countries = new ArrayList<>();

        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString("name"));
            countries.add(new Country(rs.getString("name")));
        }
        rs.close();
        return countries;

    }

}
