package rs.vegait.timesheet.jdbc;

import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.employee.*;
import rs.vegait.timesheet.core.repository.EmployeeRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.UUID;

public class JdbcEmployeeRepository implements EmployeeRepository {
    private final String TABLE_NAME = "employees";
    private Connection connection;

    public JdbcEmployeeRepository(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (id VARCHAR(255) not NULL , " +
                " firstName VARCHAR(255) not NULL, " +
                " lastName VARCHAR(255) not NULL, " +
                " username VARCHAR(255) not NULL, " +
                " emailAddress VARCHAR(255) not NULL, " +
                " requiredHoursPerWeek FLOAT not NULL, " +
                " status VARCHAR(255) not NULL, " +
                " role VARCHAR(255) not NULL, " +
                " PRIMARY KEY ( id ))");
    }

    @Override
    public void add(Employee newObject) throws SQLException {
        String sql = "INSERT INTO "+TABLE_NAME+ " VALUES ('" +
                newObject.id() +"',  '"+ newObject.name().firstName() +"','" +
                newObject.name().lastName()+"','" +
                newObject.username()+"','" +
                newObject.emailAddress()+"'," +
                newObject.requiredHoursPerWeek()+",'" +
                newObject.status()+"','" +
                newObject.role()+
                "')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

    }

    @Override
    public void remove(UUID id) throws SQLException{

    }

    @Override
    public void update(Employee newObject) throws SQLException{

    }

    @Override
    public Iterable<Employee> findAll() throws SQLException{
        return null;
    }

    @Override
    public Optional<Employee> findById(UUID id) throws SQLException {
        String sql = "SELECT * FROM "+TABLE_NAME + " WHERE id LIKE('"+id+"')";
        Employee employee = null;

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                employee = new Employee(UUID.fromString(rs.getString("id")),
                        new Name(rs.getString("firstName"),
                                rs.getString("lastName")),
                        new Username(rs.getString("username")),
                        new EmailAddress(rs.getString("emailAddress")),
                        new HoursPerWeek(rs.getDouble("requiredHoursPerWeek")),
                        rs.getString("status")=="ACTIVE" ? EmployeeStatus.ACTIVE  : EmployeeStatus.INACTIVE,
                        rs.getString("role")=="ADMIN" ? EmployeeRole.ADMIN  : EmployeeRole.WORKER);
            }
            rs.close();

        if (employee==null){
            return Optional.empty();
        }
        return Optional.of(employee);
    }

    @Override
    public Optional<Employee> findByName(String name) throws SQLException{
        return Optional.empty();
    }

    @Override
    public Page<Employee> findBy(String searchText, char firstLetter, int pageNumber, int pageSize) throws SQLException{
        return null;
    }

    @Override
    public void removeByName(String id) throws SQLException{

    }
}
