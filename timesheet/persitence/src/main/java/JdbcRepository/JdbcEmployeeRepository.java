package JdbcRepository;

import model.Page;
import model.employee.Employee;
import repository.EmployeeRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.UUID;

public class JdbcEmployeeRepository implements EmployeeRepository {
    private final String TABLE_NAME = "employees";
    private Statement statement;

    public JdbcEmployeeRepository(Connection connection) throws SQLException {
        statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (id VARCHAR(255) not NULL , " +
                " name VARCHAR(255) not NULL, " +
                " username VARCHAR(255) not NULL, " +
                " emailAddress VARCHAR(255) not NULL, " +
                " requiredHoursPerWeek INT not NULL, " +
                " status VARCHAR(255) not NULL, " +
                " role VARCHAR(255) not NULL, " +
                " PRIMARY KEY ( id ))");
    }

    @Override
    public void add(Employee newObject) {

    }

    @Override
    public void remove(UUID id) {

    }

    @Override
    public void update(Employee newObject) {

    }

    @Override
    public Iterable<Employee> findAll() {
        return null;
    }

    @Override
    public Optional<Employee> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<Employee> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Page<Employee> findBy(String searchText, char firstLetter, int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public void removeByName(String id) {

    }
}
