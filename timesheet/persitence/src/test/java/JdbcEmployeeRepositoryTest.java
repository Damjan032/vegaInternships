import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.employee.*;
import rs.vegait.timesheet.jdbc.JdbcEmployeeRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class JdbcEmployeeRepositoryTest {

    private static JdbcEmployeeRepository jdbcEmployeeRepository;
    private static Connection connection;
    private Employee testEmployee;
    private List<Employee> testList;

    @BeforeClass
    public static void setup() throws SQLException, ClassNotFoundException {
        String DB_URL = "jdbc:mysql://localhost:3306/timesheet?autoReconnect=true&useSSL=false";
        String USER = "root";
        String PASS = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        jdbcEmployeeRepository = new JdbcEmployeeRepository(connection);
    }

    @Before
    public void setUp() throws SQLException {
        initTestClients();
    }

    @Test
    public void Add_new_employee_and_find_it() throws SQLException {
        Optional<Employee> result = jdbcEmployeeRepository.findById(testEmployee.id());
        assertEquals(true, result.isPresent());
    }

    @Test
    public void Update_existing_employee_name_field() throws SQLException {
        testEmployee = testEmployee.changeName(new Name("TestFirst", "TestLast"));
        jdbcEmployeeRepository.update(testEmployee);
        Optional<Employee> result = jdbcEmployeeRepository.findById(testEmployee.id());
        assertEquals(result.get().name(), testEmployee.name());
    }

    @Test
    public void Update_existing_employee_username_filed() throws SQLException {
        testEmployee = testEmployee.changeUsername("newTestUsername");
        jdbcEmployeeRepository.update(testEmployee);
        Optional<Employee> result = jdbcEmployeeRepository.findById(testEmployee.id());
        assertEquals(result.get().username(), testEmployee.username());
    }

    @Test
    public void Update_existing_employee_password_filed() throws SQLException {
        testEmployee = testEmployee.changePassword("newPass1234");
        jdbcEmployeeRepository.update(testEmployee);
        Optional<Employee> result = jdbcEmployeeRepository.findById(testEmployee.id());
        assertEquals(result.get().password(), testEmployee.password());
    }

    @Test
    public void Update_existing_employee_email_field() throws SQLException {
        testEmployee = testEmployee.changeEmailAddress("newMail@test.com");
        jdbcEmployeeRepository.update(testEmployee);
        Optional<Employee> result = jdbcEmployeeRepository.findById(testEmployee.id());
        assertEquals(result.get().emailAddress(), testEmployee.emailAddress());
    }

    @Test
    public void Update_existing_employee_hours_per_week_field() throws SQLException {
        testEmployee = testEmployee.changeHoursPerWeek(7);
        jdbcEmployeeRepository.update(testEmployee);
        Optional<Employee> result = jdbcEmployeeRepository.findById(testEmployee.id());
        assertEquals(result.get().requiredHoursPerWeek(), testEmployee.requiredHoursPerWeek());
    }

    @Test
    public void Update_existing_employee_hours_per_role_and_status_fields() throws SQLException {
        testEmployee = testEmployee.changeRole(EmployeeRole.ADMIN);
        testEmployee = testEmployee.changeStatus(EmployeeStatus.INACTIVE);
        jdbcEmployeeRepository.update(testEmployee);
        System.out.println("ROLE " + testEmployee.role());
        Optional<Employee> result = jdbcEmployeeRepository.findById(testEmployee.id());
        assertEquals(result.get().status().toString(), testEmployee.status().toString());
        assertEquals(result.get().role().toString(), testEmployee.role().toString());
    }

    @Test
    public void Update_existing_employee_with_some_of_empty_fields() throws SQLException {
        testEmployee = testEmployee.changeStatus(null);
        testEmployee = testEmployee.changeEmailAddress("newMail@test.com");
        jdbcEmployeeRepository.update(testEmployee);
        System.out.println("ROLE " + testEmployee.role());
        Optional<Employee> result = jdbcEmployeeRepository.findById(testEmployee.id());
        assertNotNull(result.get().status());
        assertEquals(result.get().emailAddress(), testEmployee.emailAddress());
    }

    @Test
    public void Find_all_employers() throws SQLException {
        Iterable<Employee> employeeIterable = jdbcEmployeeRepository.findAll();
        AtomicInteger j = new AtomicInteger();
        employeeIterable.forEach(employee -> {
            testList.forEach(categoryFromList -> {
                if (categoryFromList.id().equals(employee.id())) {
                    j.getAndIncrement();
                }
            });
        });
        assertEquals(testList.size(), j.get());
    }

    @Test
    public void Find_pageable_employers() throws SQLException {
        List<Employee> allEmployers = Lists.newArrayList(jdbcEmployeeRepository.findAll());
        int start = 1;
        int size = 3;
        Page<Employee> employeePage = jdbcEmployeeRepository.findBy("s0", 'a', start, size);
        List<Employee> employersFromToList = Lists.newArrayList(employeePage.items());
        boolean isSame = allEmployers.get(0).id().equals(employersFromToList.get(0).id())
                && allEmployers.get(1).id().equals(employersFromToList.get(1).id())
                && allEmployers.get(2).id().equals(employersFromToList.get(2).id());
        assertEquals(true, isSame);
    }

    @Test
    public void Remove_employee_searching_by_id() throws SQLException {
        jdbcEmployeeRepository.remove(testEmployee.id());
        assertEquals(false, jdbcEmployeeRepository.findById(testEmployee.id()).isPresent());
    }

    @After
    public void clear() throws SQLException {
        jdbcEmployeeRepository.remove(testEmployee.id());
        testList.forEach(employee -> {
            try {
                jdbcEmployeeRepository.remove(employee.id());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void initTestClients() throws SQLException {
        testEmployee = new Employee(UUID.randomUUID(),
                new Name("Pera", "Pearic"),
                new Password("test1234"),
                new Username("testUser"),
                new EmailAddress("test@test.com"),
                new HoursPerWeek(7.5),
                EmployeeStatus.ACTIVE,
                EmployeeRole.WORKER);
        testList = new ArrayList<>();
        jdbcEmployeeRepository.add(testEmployee);
        for (int i = 0; i < 5; i++) {
            Employee listElement = new Employee(UUID.randomUUID(),
                    new Name("!!!!0000000" + i, "!!!!!000000" + i),
                    new Password("test1234" + i),
                    new Username("testUser" + i),
                    new EmailAddress("test@test.com" + i),
                    new HoursPerWeek(7.5),
                    EmployeeStatus.ACTIVE,
                    EmployeeRole.WORKER);
            jdbcEmployeeRepository.add(listElement);
            testList.add(listElement);
        }
    }


}
