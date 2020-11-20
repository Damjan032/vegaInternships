package rs.vegait.timesheet.jdbc;

import org.springframework.stereotype.Component;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.employee.*;
import rs.vegait.timesheet.core.repository.EmployeeRepository;

import javax.validation.constraints.NotNull;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JdbcEmployeeRepository implements EmployeeRepository {
    private final String TABLE_NAME = "employees";
    private final Connection connection;

    public JdbcEmployeeRepository(Connection connection) throws SQLException {
        this.connection = connection;
    }

    @Override
    public void add(Employee newObject) throws  SQLException{
        String sql = "INSERT INTO " + TABLE_NAME + " VALUES ('" +
                newObject.id() + "',  '" + newObject.name().firstName() + "','" +
                newObject.name().lastName() + "','" +
                newObject.username() + "','" +
                newObject.password() + "','" +
                newObject.emailAddress() + "'," +
                newObject.requiredHoursPerWeek() + ",'" +
                newObject.status() + "','" +
                newObject.role() + "','" +
                (newObject.isAccepted() ? 1 : 0) +
                "')";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.executeUpdate();

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
    public void update(Employee newObject) throws SQLException {


        Optional<Employee> employeeOptional = this.findById(newObject.id());
        if (!employeeOptional.isPresent()) {
            return;
        }
        String sql = "UPDATE " + TABLE_NAME + " SET " +
                "firstName = ?, " +
                "lastName = ?, " +
                "username = ?, " +
                "password = ?, " +
                "emailAddress = ?, " +
                "requiredHoursPerWeek = ?, " +
                "status = ?," +
                "role = ?, " +
                "wasAccepted = ? " +
                "WHERE (id = ?);";
        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1,
                newObject.name().firstName() == null ? employeeOptional.get().name().firstName() : newObject.name().firstName());
        pstmt.setString(2,
                newObject.name().lastName() == null ? employeeOptional.get().name().lastName() : newObject.name().lastName());
        pstmt.setString(3,
                newObject.username() == null ? employeeOptional.get().username() : newObject.username());
        pstmt.setString(4,
                newObject.password() == null ? employeeOptional.get().password() : newObject.password());
        pstmt.setString(5,
                newObject.emailAddress() == null ? employeeOptional.get().emailAddress() : newObject.emailAddress());
        pstmt.setDouble(6,
                newObject.requiredHoursPerWeek() <= 0 ? employeeOptional.get().requiredHoursPerWeek() : newObject.requiredHoursPerWeek());
        pstmt.setString(7,
                newObject.status() == null ? employeeOptional.get().status().toString() : newObject.status().toString());
        pstmt.setString(8,
                newObject.role() == null ? employeeOptional.get().role().toString() : newObject.role().toString());

        pstmt.setBoolean(9, newObject.isAccepted());
        pstmt.setString(10, employeeOptional.get().id().toString());

        pstmt.executeUpdate();
    }

    @Override
    public Iterable<Employee> findAll() throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY lastName, firstName";
        List<Employee> employees = new ArrayList<>();

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            employees.add(new Employee(UUID.fromString(rs.getString("id")),
                    new Name(rs.getString("firstName"),
                            rs.getString("lastName")),
                    new Password(rs.getString("password")),
                    new Username(rs.getString("username")),
                    new EmailAddress(rs.getString("emailAddress")),
                    new HoursPerWeek(rs.getDouble("requiredHoursPerWeek")),
                    rs.getString("status") == "ACTIVE" ? EmployeeStatus.ACTIVE : EmployeeStatus.INACTIVE,
                    rs.getString("role") == "ADMIN" ? EmployeeRole.ADMIN : EmployeeRole.WORKER,
                    rs.getBoolean("wasAccepted")));
        }
        rs.close();
        return employees;
    }

    @Override
    public Optional<Employee> findById(UUID id) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id LIKE(?)";
        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, id.toString());

        Employee employee = null;
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            employee = new Employee(UUID.fromString(rs.getString("id")),
                    new Name(rs.getString("firstName"),
                            rs.getString("lastName")),
                    new Password(rs.getString("password")),
                    new Username(rs.getString("username")),
                    new EmailAddress(rs.getString("emailAddress")),
                    new HoursPerWeek(rs.getDouble("requiredHoursPerWeek")),
                    rs.getString("status").equalsIgnoreCase("ACTIVE") ? EmployeeStatus.ACTIVE : EmployeeStatus.INACTIVE,
                    rs.getString("role").equalsIgnoreCase("ADMIN") ? EmployeeRole.ADMIN : EmployeeRole.WORKER,
                    rs.getBoolean("wasAccepted"));
        }
        rs.close();

        if (employee == null) {
            return Optional.empty();
        }
        return Optional.of(employee);
    }

    @Override
    public Optional<Employee> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name LIKE(?)";
        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, name);

        Employee employee = null;
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            employee = new Employee(UUID.fromString(rs.getString("id")),
                    new Name(rs.getString("firstName"),
                            rs.getString("lastName")),
                    new Password(rs.getString("password")),
                    new Username(rs.getString("username")),
                    new EmailAddress(rs.getString("emailAddress")),
                    new HoursPerWeek(rs.getDouble("requiredHoursPerWeek")),
                    rs.getString("status") == "ACTIVE" ? EmployeeStatus.ACTIVE : EmployeeStatus.INACTIVE,
                    rs.getString("role") == "ADMIN" ? EmployeeRole.ADMIN : EmployeeRole.WORKER,
                    rs.getBoolean("wasAccepted"));
        }
        rs.close();

        if (employee == null) {
            return Optional.empty();
        }
        return Optional.of(employee);
    }

    @Override
    public Page<Employee> findBy(String searchText, char firstLetter, int pageNumber, int pageSize) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY lastName, firstName LIMIT  ?, ?";
        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setInt(1, pageNumber);
        pstmt.setInt(2, pageSize);


        List<Employee> employees = new ArrayList<>();
        int numberOfRows = 0;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS total FROM " + TABLE_NAME);
        rs.next();
        numberOfRows = rs.getInt("total");

        pstmt.setInt(1, (pageNumber - 1) * pageSize);
        pstmt.setInt(2, Math.min(numberOfRows, pageNumber * pageSize));

        rs = pstmt.executeQuery();
        while (rs.next()) {
            employees.add(new Employee(UUID.fromString(rs.getString("id")),
                    new Name(rs.getString("firstName"),
                            rs.getString("lastName")),
                    new Password(rs.getString("password")),
                    new Username(rs.getString("username")),
                    new EmailAddress(rs.getString("emailAddress")),
                    new HoursPerWeek(rs.getDouble("requiredHoursPerWeek")),
                    rs.getString("status") == "ACTIVE" ? EmployeeStatus.ACTIVE : EmployeeStatus.INACTIVE,
                    rs.getString("role") == "ADMIN" ? EmployeeRole.ADMIN : EmployeeRole.WORKER,
                    rs.getBoolean("wasAccepted")));
        }
        rs.close();

        Page<Employee> employeePage = new Page<Employee>(employees, pageNumber, pageSize, numberOfRows);
        return employeePage;
    }
}
