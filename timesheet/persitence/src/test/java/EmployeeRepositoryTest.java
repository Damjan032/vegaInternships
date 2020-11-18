
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.employee.*;
import rs.vegait.timesheet.core.model.project.Category;
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

public class EmployeeRepositoryTest {

    private JdbcEmployeeRepository jdbcEmployeeRepository;
    private Employee testEmployee;

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        //String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/timesheet?autoReconnect=true&useSSL=false";
        testEmployee = new Employee(UUID.randomUUID(),
                new Name("Pera", "Pearic"),
                new Password("test1234"),
                new Username("testUser"),
                new EmailAddress("test@test.com"),
                new HoursPerWeek(7.5),
                EmployeeStatus.ACTIVE,
                EmployeeRole.WORKER);
        //  Database credentials
        String USER = "root";
        String PASS = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        jdbcEmployeeRepository = new JdbcEmployeeRepository(conn);
        jdbcEmployeeRepository.add(testEmployee);
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
        System.out.println("ROLE "+ testEmployee.role());
        Optional<Employee> result = jdbcEmployeeRepository.findById(testEmployee.id());
        assertEquals(result.get().status().toString(), testEmployee.status().toString());
        assertEquals(result.get().role().toString(), testEmployee.role().toString());
    }

    @Test
    public void Update_existing_employee_with_some_of_empty_fields() throws SQLException {
        testEmployee = testEmployee.changeStatus(null);
        testEmployee = testEmployee.changeEmailAddress("newMail@test.com");
       // testEmployee = testEmployee.changeStatus(EmployeeStatus.INACTIVE);
        jdbcEmployeeRepository.update(testEmployee);
        System.out.println("ROLE "+ testEmployee.role());
        Optional<Employee> result = jdbcEmployeeRepository.findById(testEmployee.id());
        assertNotNull(result.get().status());
        assertEquals(result.get().emailAddress(), testEmployee.emailAddress());
    }

    @Test
    public void Find_all_employers() throws  SQLException {
        List<Employee> employeeList = new ArrayList<>();
        Employee testEmployee;
        int i = 0;
        for(i = 0; i<5; i++){
            testEmployee = new Employee(UUID.randomUUID(),
                    new Name("Pera"+i, "Pearic"+i),
                    new Password("test1234"+i),
                    new Username("testUser"+i),
                    new EmailAddress("test@test.com"+i),
                    new HoursPerWeek(7.5),
                    EmployeeStatus.ACTIVE,
                    EmployeeRole.WORKER);
            jdbcEmployeeRepository.add(testEmployee);
            employeeList.add(testEmployee);
        }
        Iterable<Employee> employeeIterable = jdbcEmployeeRepository.findAll();
        AtomicInteger j = new AtomicInteger();
        employeeIterable.forEach(category -> {
            employeeList.forEach(categoryFromList ->{
                if(categoryFromList.id().equals(category.id())) {
                    j.getAndIncrement();
                }
            });
        });
        for(int k = 0; k<5; k++) jdbcEmployeeRepository.remove(employeeList.get(k).id());
        assertEquals(i,j.get());
    }

    @Test
    public void Find_pageable_employers() throws SQLException {
        List<Employee> employeeList = new ArrayList<>();
        Employee testEmployee;
        int i = 0;
        for(i = 0; i<5; i++){
            testEmployee = new Employee(UUID.randomUUID(),
                    new Name("Pera"+i, "Pearic"+i),
                    new Password("test1234"+i),
                    new Username("testUser"+i),
                    new EmailAddress("test@test.com"+i),
                    new HoursPerWeek(7.5),
                    EmployeeStatus.ACTIVE,
                    EmployeeRole.WORKER);
            jdbcEmployeeRepository.add(testEmployee);
            employeeList.add(testEmployee);
        }
        List<Employee> allEmployers = Lists.newArrayList(jdbcEmployeeRepository.findAll());
        int start = 1;
        int size = 3;
        Page<Employee> employeePage = jdbcEmployeeRepository.findBy("s0",'a',start,size);
        List<Employee> employersFromToList = Lists.newArrayList(employeePage.items());
        boolean isSame = allEmployers.get(1).id().equals(employersFromToList.get(0).id())
                && allEmployers.get(2).id().equals(employersFromToList.get(1).id())
                && allEmployers.get(3).id().equals(employersFromToList.get(2).id());
        assertEquals(true,isSame);
        for(int k = 0; k<5; k++) jdbcEmployeeRepository.remove(employeeList.get(k).id());
    }

    @Test
    public void Remove_employee_searching_by_id() throws SQLException{
        jdbcEmployeeRepository.remove(testEmployee.id());
        assertEquals(false,jdbcEmployeeRepository.findById(testEmployee.id()).isPresent());
    }

    @After
    public void clear() throws SQLException {
        jdbcEmployeeRepository.remove(testEmployee.id());
    }




}
