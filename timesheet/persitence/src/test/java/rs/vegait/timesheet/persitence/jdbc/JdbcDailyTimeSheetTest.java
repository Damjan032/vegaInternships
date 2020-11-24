package rs.vegait.timesheet.persitence.jdbc;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import rs.vegait.timesheet.core.model.client.*;
import rs.vegait.timesheet.core.model.employee.*;
import rs.vegait.timesheet.core.model.project.Category;
import rs.vegait.timesheet.core.model.project.Project;
import rs.vegait.timesheet.core.model.project.ProjectName;
import rs.vegait.timesheet.core.model.project.ProjectStatus;
import rs.vegait.timesheet.core.model.timesheet.DailyTimeSheet;
import rs.vegait.timesheet.core.model.timesheet.SpentTime;
import rs.vegait.timesheet.core.model.timesheet.TimeSheet;

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
    private JdbcProjectRepository jdbcProjectRepository;
    private TimeSheet testTimeSheet;
    private Project testProject;
    private JdbcCategoryRepository jdbcCategoryRepository;
    private Category testCategory;
    private JdbcClientRepository jdbcClientRepository;
    private Client testClient;

    @BeforeClass
    public static void setup() throws Exception {
        String DB_URL = "jdbc:mysql://localhost:3306/timesheet?autoReconnect=true&useSSL=false";
        String USER = "root";
        String PASS = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
    }

    @Test
    public void Find_all_daily_sheets() throws Exception {
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
    public void Find_all_daily_sheets_for_specified_employee() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Iterable<DailyTimeSheet> dailyTimeSheetIterable = jdbcDailyTimeSheetRepository.
                findDailyTimeSheetsForEmployee(testDailyTimeSheet.employee(), sdf.parse("31-12-0000"), sdf.parse("31-12-1000"));
        AtomicInteger j = new AtomicInteger();
        AtomicBoolean isSame = new AtomicBoolean(true);
        dailyTimeSheetIterable.forEach(dailyTimeSheet -> {
            if (!dailyTimeSheet.employee().id().equals(testDailyTimeSheet.employee().id()))
                isSame.set(false);
        });
        TestCase.assertEquals(true, isSame.get());
    }


    @Before
    public void setUp() throws Exception, ParseException {
        jdbcEmployeeRepository = new JdbcEmployeeRepository(this.connection);
        initTests();
    }

    @Test
    public void Add_new_dailyTimeSheet_and_find_it() throws Exception {
        Optional<DailyTimeSheet> result = jdbcDailyTimeSheetRepository.findById(testDailyTimeSheet.id());
        assertEquals(true, result.isPresent());
    }

    @After
    public void remove_tests_classes() throws Exception {
        jdbcDailyTimeSheetRepository.remove(testDailyTimeSheet.id());
        testListDailyTimeSheet.forEach(dailyTimeSheet -> {
            try {
                jdbcDailyTimeSheetRepository.remove(dailyTimeSheet.id());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        jdbcEmployeeRepository.remove(testDailyTimeSheet.employee().id());
        jdbcProjectRepository.remove(testProject.id());
        jdbcCategoryRepository.remove(testCategory.id());
        jdbcClientRepository.remove(testClient.id());

    }

    public void initTests() throws Exception {
        jdbcCategoryRepository = new JdbcCategoryRepository(this.connection);
        jdbcClientRepository = new JdbcClientRepository(this.connection);
        jdbcProjectRepository = new JdbcProjectRepository(this.connection, jdbcClientRepository, jdbcEmployeeRepository);
        jdbcDailyTimeSheetRepository = new JdbcDailyTimeSheetRepository(this.connection, jdbcEmployeeRepository, jdbcCategoryRepository,
                jdbcProjectRepository);
        this.testListDailyTimeSheet = new ArrayList<>();
        Employee testEmployee = new Employee(UUID.randomUUID(),
                new Name("Pera Pearic"),
                new Username("testUser"),
                Optional.empty(),
                new EmailAddress("test@test.com"),
                new HoursPerWeek(7.5),
                EmployeeStatus.ACTIVE,
                EmployeeRole.WORKER,
                true);
        jdbcEmployeeRepository.add(testEmployee);
        testCategory = new Category(UUID.randomUUID(), "TEST_CAT");
        jdbcCategoryRepository.add(testCategory);
        testClient = new Client(UUID.randomUUID(), new ClientName("Client123"),
                new Address(
                        new Street("Jump", "21"),
                        new City("New York", 10001),
                        new Country("United States")
                ));
        jdbcClientRepository.add(testClient);

        String dateString = "31-12-0000";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");


        testProject = new Project(UUID.randomUUID(), Optional.empty(), new ProjectName("TestProject"), ProjectStatus.ACTIVE, testEmployee, testClient);
        testTimeSheet = new TimeSheet(new SpentTime(5), Optional.empty(), Optional.empty(), testProject, testCategory);
        List<TimeSheet> timeSheets = new ArrayList<>();
        jdbcProjectRepository.add(testProject);
        testDailyTimeSheet = new DailyTimeSheet(UUID.randomUUID(), testEmployee, dateFormat.parse(dateString), timeSheets);
        jdbcDailyTimeSheetRepository.add(testDailyTimeSheet);
        timeSheets.add(testTimeSheet);
        for (int i = 0; i < 5; i++) {
            dateString = "31-0" + i + "-0001";
            List<TimeSheet> arrayList = new ArrayList<>();
            arrayList.add(testTimeSheet);
            DailyTimeSheet liseElement = new DailyTimeSheet(UUID.randomUUID(), testEmployee, dateFormat.parse(dateString),
                    timeSheets);
            jdbcDailyTimeSheetRepository.add(liseElement);
            testListDailyTimeSheet.add(liseElement);
        }
    }

}
