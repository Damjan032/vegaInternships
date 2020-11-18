
import org.junit.Before;
import org.junit.Test;
import rs.vegait.timesheet.core.model.employee.*;
import rs.vegait.timesheet.jdbc.JdbcEmployeeRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

public class EmployeeRepositoryTest {

    private JdbcEmployeeRepository jdbcEmployeeRepository;
    private Employee testEmployee;

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        //String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/timesheet?autoReconnect=true&useSSL=false";
        testEmployee = new Employee(UUID.randomUUID(),
                new Name("Pera", "Pearic"),
                new Username("testUser"),
                new EmailAddress("test@test.com"),
                new HoursPerWeek(7.5),
                EmployeeStatus.ACTIVE,
                EmployeeRole.WORKER);
        //  Database credentials
        String USER = "root";
        String PASS = "root";
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        jdbcEmployeeRepository = new JdbcEmployeeRepository(conn);
        jdbcEmployeeRepository.add(testEmployee);
    }

    @Test
    public void addAndFindByIdTrue() throws SQLException {
        Optional<Employee> result = jdbcEmployeeRepository.findById(testEmployee.id());
        assertEquals(true, result.isPresent());
    }




}
