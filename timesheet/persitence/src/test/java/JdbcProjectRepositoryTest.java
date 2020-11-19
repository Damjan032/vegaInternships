import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.client.*;
import rs.vegait.timesheet.core.model.employee.*;
import rs.vegait.timesheet.core.model.project.Category;
import rs.vegait.timesheet.core.model.project.Project;
import rs.vegait.timesheet.core.model.project.ProjectName;
import rs.vegait.timesheet.core.model.project.ProjectStatus;
import rs.vegait.timesheet.jdbc.JdbcCategoryRepository;
import rs.vegait.timesheet.jdbc.JdbcClientRepository;
import rs.vegait.timesheet.jdbc.JdbcEmployeeRepository;
import rs.vegait.timesheet.jdbc.JdbcProjectRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.TestCase.assertEquals;

public class JdbcProjectRepositoryTest {
    private static Connection connection;
    private JdbcProjectRepository jdbcProjectRepository;
    private Project testProject;
    private List<Project> testProjectsList;

    @BeforeClass
    public static void setup() throws SQLException, ClassNotFoundException {
        String DB_URL = "jdbc:mysql://localhost:3306/timesheet?autoReconnect=true&useSSL=false";
        String USER = "root";
        String PASS = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
    }

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        initTestClients();
    }

    @Test
    public void Add_new_project_and_find_it() throws SQLException {
        Optional<Project> result = jdbcProjectRepository.findById(testProject.id());
        assertEquals(true, result.isPresent());
    }

    @Test
    public void Find_all_projects() throws SQLException {
        Iterable<Project> projectsIterable = jdbcProjectRepository.findAll();
        AtomicInteger j = new AtomicInteger();
        projectsIterable.forEach(project -> {
            testProjectsList.forEach(categoryFromList -> {
                if (categoryFromList.id().equals(project.id())) {
                    j.getAndIncrement();
                }
            });
        });
        assertEquals(testProjectsList.size(), j.get());
    }

    @Test
    public void Rename_existing_project() throws SQLException {
        testProject = testProject.rename(new ProjectName("123Project"));
        jdbcProjectRepository.update(testProject);
        assertEquals(testProject.name().name(), jdbcProjectRepository.findById(testProject.id()).get().name().name());
    }

    @Test
    public void Update_status_of_existing_project() throws SQLException {
        testProject = testProject.changeStatus(ProjectStatus.ARCHIVE);
        jdbcProjectRepository.update(testProject);
        jdbcProjectRepository.findById(testProject.id());
        assertEquals(testProject.status().toString(), jdbcProjectRepository.findById(testProject.id()).get().status().toString());
    }

    @Test
    public void Search_project_pageable_method_only_pageable() throws SQLException {
        List<Project> allProjects = Lists.newArrayList(jdbcProjectRepository.findAll());
        int start = 1;
        int size = 3;
        Page<Project> projectPage = jdbcProjectRepository.findBy("", ' ', start, size);
        List<Project> projectsFromToList = Lists.newArrayList(projectPage.items());
        boolean isSame = allProjects.get(0).id().equals(projectsFromToList.get(0).id())
                && allProjects.get(1).id().equals(projectsFromToList.get(1).id())
                && allProjects.get(2).id().equals(projectsFromToList.get(2).id());
        assertEquals(true, isSame);
    }

    @Test
    public void Search_employers_pageable_method_only_search() throws SQLException {
        List<UUID> listToCheck = Arrays.asList(testProjectsList.get(4).id(), testProjectsList.get(0).id(), testProjectsList.get(2).id());
        List<UUID> resultsList = new ArrayList<>();
        int start = 1;
        int size = 3;
        Page<Project> clientPage = jdbcProjectRepository.findBy("qwe", ' ', start, size);
        List<Project> clientsFromToList = Lists.newArrayList(clientPage.items());
        clientsFromToList.forEach(client -> {
            resultsList.add(client.id());
        });
        assertEquals(listToCheck, resultsList);
    }

    @Test
    public void Search_employers_pageable_method_only_first_letter() throws SQLException {
        List<UUID> listToCheck = Arrays.asList(testProjectsList.get(1).id(), testProjectsList.get(4).id());
        List<UUID> resultsList = new ArrayList<>();
        int start = 1;
        int size = 3;
        Page<Project> clientPage = jdbcProjectRepository.findBy("", '!', start, size);
        List<Project> clientsFromToList = Lists.newArrayList(clientPage.items());
        clientsFromToList.forEach(client -> {
            resultsList.add(client.id());
        });
        assertEquals(listToCheck, resultsList);
    }

    @Test
    public void Search_employers_pageable_method_search_first_letter() throws SQLException {
        List<UUID> listToCheck = Arrays.asList(testProjectsList.get(4).id());
        List<UUID> resultsList = new ArrayList<>();
        int start = 1;
        int size = 3;
        Page<Project> clientPage = jdbcProjectRepository.findBy("qwe", '!', start, size);
        List<Project> clientsFromToList = Lists.newArrayList(clientPage.items());
        clientsFromToList.forEach(client -> {
            resultsList.add(client.id());
        });
        assertEquals(listToCheck, resultsList);
    }

    @After
    public void Remove_tests_classes() throws SQLException {
        jdbcProjectRepository.remove(testProject.id());
        testProjectsList.forEach(project -> {
            try {
                jdbcProjectRepository.remove(project.id());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        JdbcClientRepository jdbcClientRepository = new JdbcClientRepository(this.connection);
        jdbcClientRepository.remove(testProject.client().id());

        JdbcCategoryRepository jdbcCategoryRepository = new JdbcCategoryRepository(this.connection);
        jdbcCategoryRepository.remove(testProject.category().id());

        JdbcEmployeeRepository jdbcEmployeeRepository = new JdbcEmployeeRepository(this.connection);
        jdbcEmployeeRepository.remove(testProject.teamLead().id());
    }

    private void initTestClients() throws SQLException {
        Employee testEmployee = new Employee(UUID.randomUUID(),
                new Name("Pera", "Pearic"),
                new Password("test1234"),
                new Username("testUser"),
                new EmailAddress("test@test.com"),
                new HoursPerWeek(7.5),
                EmployeeStatus.ACTIVE,
                EmployeeRole.WORKER);
        JdbcEmployeeRepository jdbcEmployeeRepository = new JdbcEmployeeRepository(this.connection);
        jdbcEmployeeRepository.add(testEmployee);
        Category testCategory = new Category(UUID.randomUUID(), "TEST_CAT");
        JdbcCategoryRepository jdbcCategoryRepository = new JdbcCategoryRepository(this.connection);
        jdbcCategoryRepository.add(testCategory);
        Client testClient = new Client(UUID.randomUUID(), new ClientName("Client123"),
                new Address(
                        new Street("Jump", "21"),
                        new City("New York", 10001),
                        new Country("United States")
                ));
        JdbcClientRepository jdbcClientRepository = new JdbcClientRepository(this.connection);
        jdbcClientRepository.add(testClient);

        testProject = new Project(UUID.randomUUID(), Optional.empty(), new ProjectName("TestProject"), ProjectStatus.ACTIVE, testEmployee, testClient, testCategory);
        jdbcProjectRepository = new JdbcProjectRepository(this.connection, jdbcClientRepository, jdbcEmployeeRepository, jdbcCategoryRepository);
        jdbcProjectRepository.add(testProject);
        testProjectsList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String name = "";
            switch (i) {
                case 0:
                    name = "####00000000qwe";
                    break;
                case 1:
                    name = "!!!000000001";
                    break;
                case 2:
                    name = "###00000qwe000";
                    break;
                case 3:
                    name = "###0000adadqsweasads";
                    break;
                case 4:
                    name = "!!!0000adadqweasads";
                    break;
            }
            Project listElement = new Project(UUID.randomUUID(),
                    Optional.empty(),
                    new ProjectName(name),
                    ProjectStatus.ACTIVE,
                    testProject.teamLead(),
                    testProject.client(),
                    testProject.category());

            jdbcProjectRepository.add(listElement);
            testProjectsList.add(listElement);
        }
    }


}
