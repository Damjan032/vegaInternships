package rs.vegait.timesheet.persitence.jdbc;

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
import rs.vegait.timesheet.core.model.timesheet.TimeSheetDescription;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

public class JdbcTimeSheetsRepositoryTest {
    private static Connection connection;
    private static JdbcTimeSheetRepository jdbcTimeSheetRepository;
    private static JdbcProjectRepository jdbcProjectRepository;
    private static JdbcDailyTimeSheetRepository jdbcDailyTimeSheetRepository;
    private TimeSheet testTimeSheet;
    private List<TimeSheet> testList;

    @BeforeClass
    public static void setup() throws Exception {
        String DB_URL = "jdbc:mysql://localhost:3306/timesheet?autoReconnect=true&useSSL=false";
        String USER = "root";
        String PASS = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
    }

    @Before
    public void setUp() throws Exception {
        initTests();

    }

    @Test
    public void Update_time_of_work_in_existing_time_sheet() throws Exception {
        double newTime = 1;
        testTimeSheet = testTimeSheet.changeTimeOfWork(new SpentTime(1));
        jdbcTimeSheetRepository.update(testTimeSheet);
        assertEquals(newTime, jdbcTimeSheetRepository.findById(testTimeSheet.id()).get().time().numberOfHours());
    }

    @Test
    public void Update_description_of_work_in_existing_time_sheet() throws Exception {
        String description = "Hey";
        testTimeSheet = testTimeSheet.makeNewDescription(Optional.of(new TimeSheetDescription(description)));
        jdbcTimeSheetRepository.update(testTimeSheet);
        assertEquals(description, jdbcTimeSheetRepository.findById(testTimeSheet.id()).get().description().description());
    }

    @Test
    public void Add_new_time_sheet_and_find_it() throws Exception {
        Optional<TimeSheet> result = jdbcTimeSheetRepository.findById(testTimeSheet.id());
        assertEquals(true, result.isPresent());
    }

    @Test
    public void Remove_time_sheet() throws Exception {
        jdbcTimeSheetRepository.remove(testTimeSheet.id());
        Optional<TimeSheet> result = jdbcTimeSheetRepository.findById(testTimeSheet.id());
        assertEquals(false, result.isPresent());
    }


    @After
    public void Remove_tests_classes() throws Exception {
        JdbcEmployeeRepository jdbcEmployeeRepository = new JdbcEmployeeRepository(connection);
        JdbcCategoryRepository jdbcCategoryRepository = new JdbcCategoryRepository(connection);
        JdbcClientRepository jdbcClientRepository = new JdbcClientRepository(connection);
        this.jdbcDailyTimeSheetRepository = new JdbcDailyTimeSheetRepository(connection, jdbcEmployeeRepository);

        jdbcTimeSheetRepository.remove(testTimeSheet.id());
        jdbcDailyTimeSheetRepository.remove(testTimeSheet.dailyTimeSheet().id());

        testList.forEach(timeSheet -> {
            try {
                jdbcTimeSheetRepository.remove(timeSheet.id());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        jdbcDailyTimeSheetRepository.remove(testList.get(0).dailyTimeSheet().id());
        jdbcProjectRepository.remove(testTimeSheet.project().id());
        jdbcClientRepository.remove(testTimeSheet.project().client().id());
        jdbcCategoryRepository.remove(testTimeSheet.project().category().id());
        jdbcEmployeeRepository.remove(testTimeSheet.project().teamLead().id());
    }

    private void initTests() throws Exception {
        Project testProject;
        Employee testEmployee = new Employee(UUID.randomUUID(),
                new Name("Pera Pearic"),
                new Username("testUser"),
                Optional.empty(),
                new EmailAddress("test@test.com"),
                new HoursPerWeek(7.5),
                EmployeeStatus.ACTIVE,
                EmployeeRole.WORKER,
                true);
        JdbcEmployeeRepository jdbcEmployeeRepository = new JdbcEmployeeRepository(connection);
        jdbcEmployeeRepository.add(testEmployee);
        Category testCategory = new Category(UUID.randomUUID(), "TEST_CAT");
        JdbcCategoryRepository jdbcCategoryRepository = new JdbcCategoryRepository(connection);
        jdbcCategoryRepository.add(testCategory);
        Client testClient = new Client(UUID.randomUUID(), new ClientName("Client123"),
                new Address(
                        new Street("Jump", "21"),
                        new City("New York", 10001),
                        new Country("United States")
                ));
        JdbcClientRepository jdbcClientRepository = new JdbcClientRepository(connection);
        jdbcClientRepository.add(testClient);

        testProject = new Project(UUID.randomUUID(), Optional.empty(), new ProjectName("TestProject"), ProjectStatus.ACTIVE, testEmployee, testClient, testCategory);
        jdbcProjectRepository = new JdbcProjectRepository(connection, jdbcClientRepository, jdbcEmployeeRepository, jdbcCategoryRepository);
        jdbcProjectRepository.add(testProject);

        String dateString = "31-12-0000";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DailyTimeSheet testDailyTimeSheet = new DailyTimeSheet(UUID.randomUUID(), testEmployee, dateFormat.parse(dateString));
        JdbcDailyTimeSheetRepository jdbcDailyTimeSheetRepository = new JdbcDailyTimeSheetRepository(connection, jdbcEmployeeRepository);
        jdbcDailyTimeSheetRepository.add(testDailyTimeSheet);
        testTimeSheet = new TimeSheet(UUID.randomUUID(), new SpentTime(7), Optional.empty(), Optional.empty(), testProject, testDailyTimeSheet);
        jdbcTimeSheetRepository = new JdbcTimeSheetRepository(connection, jdbcProjectRepository, jdbcDailyTimeSheetRepository);
        jdbcTimeSheetRepository.add(testTimeSheet);
        testList = new ArrayList<>();
        DailyTimeSheet testDailyTimeSheet2 = new DailyTimeSheet(UUID.randomUUID(), testEmployee, dateFormat.parse(dateString));
        jdbcDailyTimeSheetRepository.add(testDailyTimeSheet2);
        for (int i = 0; i < 5; i++) {
            TimeSheet liseElement = new TimeSheet(UUID.randomUUID(),
                    new SpentTime(i + 1),
                    Optional.empty(), Optional.empty(),
                    testProject,
                    testDailyTimeSheet2);

            testList.add(liseElement);
            jdbcTimeSheetRepository.add(liseElement);
        }
    }
}
