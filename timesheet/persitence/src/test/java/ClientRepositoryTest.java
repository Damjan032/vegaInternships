import org.junit.Before;
import org.junit.Test;
import rs.vegait.timesheet.core.model.client.*;
import rs.vegait.timesheet.core.model.project.Category;
import rs.vegait.timesheet.jdbc.JdbcCategoryRepository;
import rs.vegait.timesheet.jdbc.JdbcClientRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

public class ClientRepositoryTest {
    private JdbcClientRepository jdbcClientRepository;
    private Client testClient;

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        //String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/timesheet?autoReconnect=true&useSSL=false";
        //testCategory = new Category(UUID.randomUUID(), "TEST_CAT");
        //  Database credentials
        String USER = "root";
        String PASS = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        testClient = new Client(UUID.randomUUID(), new ClientName("Client123"),
                new Address(
                        new Street("Jump","21"),
                        new City("New York", 10001),
                        new Country("United States")
                ));
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        jdbcClientRepository = new JdbcClientRepository(conn);
    }

    @Test
    public void Add_new_client_and_find_client() throws SQLException {
        System.out.println(testClient.name());
       jdbcClientRepository.add(testClient);
    }
}
