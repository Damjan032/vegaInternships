import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import rs.vegait.timesheet.core.model.employee.*;
import rs.vegait.timesheet.core.model.timesheet.DailyTimeSheet;
import rs.vegait.timesheet.jdbc.JdbcDailyTimeSheetRepository;
import rs.vegait.timesheet.jdbc.JdbcEmployeeRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcDailyTimeSheetTest {
    private static Connection connection;
    private JdbcDailyTimeSheetRepository jdbcDailyTimeSheetRepository;
    private DailyTimeSheet testDailyTimeSheet;
    private List<DailyTimeSheet> testListDailyTimeSheet;
    private JdbcEmployeeRepository jdbcEmployeeRepository;

    @BeforeClass
    public static void setup() throws SQLException, ClassNotFoundException {
        String DB_URL = "jdbc:mysql://localhost:3306/timesheet?autoReconnect=true&useSSL=false";
        String USER = "root";
        String PASS = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
    }

    @Test
    public void Find_all_daily_sheets() throws SQLException {
        Iterable<DailyTimeSheet> dailyTimeSheetIterable = jdbcDailyTimeSheetRepository.findAll();
        AtomicInteger j = new AtomicInteger();
        dailyTimeSheetIterable.forEach(dailyTimeSheet -> {
            testListDailyTimeSheet.forEach(sheetFromList -> {
                if (sheetFromList.id().equals(dailyTimeSheet.id())) {
                    j.getAndIncrement();
                }
            });
        });
        TestCase.assertEquals(testListDailyTimeSheet.size(), j.get());
    }

    @Test
    public void Find_all_daily_sheets_for_specified_employee() throws SQLException {
        Iterable<DailyTimeSheet> dailyTimeSheetIterable = jdbcDailyTimeSheetRepository.findDailyTimeSheetsForEmployer(testDailyTimeSheet.employee());
        AtomicInteger j = new AtomicInteger();
        AtomicBoolean isSame = new AtomicBoolean(true);
        dailyTimeSheetIterable.forEach(dailyTimeSheet -> {
            if (!dailyTimeSheet.employee().id().equals(testDailyTimeSheet.employee().id()))
                isSame.set(false);
        });
        TestCase.assertEquals(true, isSame.get());
    }


    @Before
    public void setUp() throws SQLException, ParseException {
        jdbcEmployeeRepository = new JdbcEmployeeRepository(this.connection);
        jdbcDailyTimeSheetRepository = new JdbcDailyTimeSheetRepository(this.connection, jdbcEmployeeRepository);
        initTests();
    }

    @Test
    public void Add_new_dailyTimeSheet_and_find_it() throws SQLException {
        Optional<DailyTimeSheet> result = jdbcDailyTimeSheetRepository.findById(testDailyTimeSheet.id());
        assertEquals(true, result.isPresent());
    }

    @After
    public void remove_tests_classes() throws SQLException {
        jdbcDailyTimeSheetRepository.remove(testDailyTimeSheet.id());
        testListDailyTimeSheet.forEach(dailyTimeSheet -> {
            try {
                jdbcDailyTimeSheetRepository.remove(dailyTimeSheet.id());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        jdbcEmployeeRepository.remove(testDailyTimeSheet.employee().id());
    }

    public void initTests() throws SQLException, ParseException {
        this.testListDailyTimeSheet = new ArrayList<>();
        Employee testEmployee = new Employee(UUID.randomUUID(),
                new Name("Pera", "Pearic"),
                new Password("test1234"),
                new Username("testUser"),
                new EmailAddress("test@test.com"),
                new HoursPerWeek(7.5),
                EmployeeStatus.ACTIVE,
                EmployeeRole.WORKER);

        jdbcEmployeeRepository.add(testEmployee);
        String dateString = "31-12-0000";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        testDailyTimeSheet = new DailyTimeSheet(UUID.randomUUID(), testEmployee, dateFormat.parse(dateString));

        for (int i = 0; i < 5; i++) {
            dateString = "31-0" + i + "-0001";
            DailyTimeSheet liseElement = new DailyTimeSheet(UUID.randomUUID(), testEmployee, dateFormat.parse(dateString));
            jdbcDailyTimeSheetRepository.add(liseElement);
            testListDailyTimeSheet.add(liseElement);
        }
        jdbcDailyTimeSheetRepository.add(testDailyTimeSheet);
    }

}
