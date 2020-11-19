import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.client.*;
import rs.vegait.timesheet.jdbc.JdbcClientRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.TestCase.assertEquals;

public class JdbcClientRepositoryTest {
    private static JdbcClientRepository jdbcClientRepository;
    private static Connection connection;
    private Client testClient;
    private List<Client> testList;

    @BeforeClass
    public static void setup() throws SQLException, ClassNotFoundException {
        String DB_URL = "jdbc:mysql://localhost:3306/timesheet?autoReconnect=true&useSSL=false";
        String USER = "root";
        String PASS = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        jdbcClientRepository = new JdbcClientRepository(connection);
    }

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        initTestClients();
    }

    @Test
    public void Add_new_client_and_find_client() throws SQLException {
        assertEquals(testClient.id(), jdbcClientRepository.findById(testClient.id()).get().id());
    }

    @Test
    public void Rename_existing_client() throws SQLException {
        testClient = testClient.changeName(new ClientName("123Client"));
        jdbcClientRepository.update(testClient);
        assertEquals(testClient.name(), jdbcClientRepository.findById(testClient.id()).get().name());
    }

    @Test
    public void Find_all_clients() throws SQLException {
        Iterable<Client> clientIterable = jdbcClientRepository.findAll();
        AtomicInteger j = new AtomicInteger();
        clientIterable.forEach(category -> {
            testList.forEach(categoryFromList -> {
                if (categoryFromList.id().equals(category.id())) {
                    j.getAndIncrement();
                }
            });
        });
        assertEquals(testList.size(), j.get());
    }

    @Test
    public void Search_employers_pageable_method_only_pageable() throws SQLException {
        List<Client> allClients = Lists.newArrayList(jdbcClientRepository.findAll());
        int start = 1;
        int size = 3;
        Page<Client> clientPage = jdbcClientRepository.findBy("", ' ', start, size);
        List<Client> clientsFromToList = Lists.newArrayList(clientPage.items());
        boolean isSame = allClients.get(0).id().equals(clientsFromToList.get(0).id())
                && allClients.get(1).id().equals(clientsFromToList.get(1).id())
                && allClients.get(2).id().equals(clientsFromToList.get(2).id());
        assertEquals(true, isSame);
    }

    @Test
    public void Search_employers_pageable_method_only_first_letter() throws SQLException {
        List<UUID> listToCheck = Arrays.asList(testList.get(0).id(), testList.get(2).id(), testList.get(4).id());
        List<UUID> resultsList = new ArrayList<>();
        int start = 1;
        int size = 3;
        Page<Client> clientPage = jdbcClientRepository.findBy("qwr", ' ', start, size);
        List<Client> clientsFromToList = Lists.newArrayList(clientPage.items());
        clientsFromToList.forEach(client -> {
            resultsList.add(client.id());
        });
        assertEquals(listToCheck, resultsList);
    }

    @Test
    public void Search_employers_pageable_method_only_search() throws SQLException {
        List<UUID> listToCheck = Arrays.asList(testList.get(0).id(), testList.get(2).id(), testList.get(4).id());
        List<UUID> resultsList = new ArrayList<>();
        int start = 1;
        int size = 3;
        Page<Client> clientPage = jdbcClientRepository.findBy("qwr", ' ', start, size);
        List<Client> clientsFromToList = Lists.newArrayList(clientPage.items());
        clientsFromToList.forEach(client -> {
            resultsList.add(client.id());
        });
        assertEquals(listToCheck, resultsList);
    }

    @Test
    public void Search_clients_pageable_method_search_and_first_letter() throws SQLException {
        List<UUID> listToCheck = Arrays.asList(testList.get(0).id());
        List<UUID> resultsList = new ArrayList<>();
        int start = 1;
        int size = 3;
        Page<Client> clientPage = jdbcClientRepository.findBy("qwr", 'q', start, size);
        List<Client> clientsFromToList = Lists.newArrayList(clientPage.items());
        clientsFromToList.forEach(client -> {
            resultsList.add(client.id());
        });
        assertEquals(listToCheck, resultsList);
    }


    @Test
    public void Update_address_of_existing_client() throws SQLException {
        testClient = testClient.changeAddress(new Address(
                new Street("Jump", "21"),
                new City("New York", 10002),
                new Country("United States")
        ));
        jdbcClientRepository.update(testClient);
        assertEquals(testClient.address(), jdbcClientRepository.findById(testClient.id()).get().address());
    }

    @After
    public void deleteTestClients() throws SQLException {
        jdbcClientRepository.remove(testClient.id());
        testList.forEach(client -> {
            try {
                jdbcClientRepository.remove(client.id());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void initTestClients() throws SQLException {
        testClient = new Client(UUID.randomUUID(), new ClientName("Client123"),
                new Address(
                        new Street("Jump", "21"),
                        new City("New York", 10001),
                        new Country("United States")
                ));
        testList = new ArrayList<>();
        testList.add(new Client(UUID.randomUUID(), new ClientName("!!!!00000Client"),
                new Address(
                        new Street("qwrJump", "21"),
                        new City("New York", 10001),
                        new Country("United States")
                )));
        testList.add(new Client(UUID.randomUUID(), new ClientName("!!!!00001Client"),
                new Address(
                        new Street("Jump", "21"),
                        new City("New York", 10001),
                        new Country("United States")
                )));
        testList.add(new Client(UUID.randomUUID(), new ClientName("!!!!00002Client"),
                new Address(
                        new Street("Jump", "21qwr"),
                        new City("New York", 10001),
                        new Country("United States")
                )));
        testList.add(new Client(UUID.randomUUID(), new ClientName("!!!!00003Client"),
                new Address(
                        new Street("Jump", "21"),
                        new City("New York", 10001),
                        new Country("United States")
                )));
        testList.add(new Client(UUID.randomUUID(), new ClientName("!!!!00004Client"),
                new Address(
                        new Street("CJump", "21"),
                        new City("New YorkqwR", 10001),
                        new Country("United States")
                )));
        jdbcClientRepository.add(testClient);
        testList.forEach(client -> {
            try {
                jdbcClientRepository.add(client);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }
}
