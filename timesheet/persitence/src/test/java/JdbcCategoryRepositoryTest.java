import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.project.Category;
import rs.vegait.timesheet.jdbc.JdbcCategoryRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.TestCase.assertEquals;

public class JdbcCategoryRepositoryTest {

    private static Connection connection;
    private static JdbcCategoryRepository jdbcCategoryRepository;
    private Category testCategory;
    private List<Category> testList;

    @BeforeClass
    public static void setup() throws SQLException, ClassNotFoundException {
        String DB_URL = "jdbc:mysql://localhost:3306/timesheet?autoReconnect=true&useSSL=false";
        String USER = "root";
        String PASS = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        jdbcCategoryRepository = new JdbcCategoryRepository(connection);
    }

    @Before
    public void setUp() throws SQLException {
        initTests();
    }

    @Test
    public void Add_new_category_and_find_it() throws SQLException {
        Optional<Category> result = jdbcCategoryRepository.findById(testCategory.id());
        assertEquals(true, result.isPresent());
    }

    @Test
    public void Find_category_by_existing_name() throws SQLException {
        Optional<Category> category = jdbcCategoryRepository.findByName(testCategory.name());
        assertEquals(testCategory, category.get());
    }

    @Test
    public void Doesnt_find_category_by_non_existing_name() throws SQLException {
        Optional<Category> falseRes = jdbcCategoryRepository.findByName("PERA");
        assertEquals(false, falseRes.isPresent());
    }

    @Test
    public void Update_existing_category() throws SQLException {
        String newName = "NEW_NAME";
        testCategory = testCategory.rename(newName);
        jdbcCategoryRepository.update(testCategory);
        Optional<Category> result = jdbcCategoryRepository.findByName(newName);
        assertEquals(true, result.isPresent());
    }

    @Test
    public void Remove_category_searching_by_id() throws SQLException {
        jdbcCategoryRepository.remove(testCategory.id());
        Optional<Category> deleted = jdbcCategoryRepository.findById(testCategory.id());
        assertEquals(false, deleted.isPresent());
    }

    @Test
    public void Remove_category_searching_by_name() throws SQLException {
        jdbcCategoryRepository.removeByName(testCategory.name());
        Optional<Category> deleted = jdbcCategoryRepository.findById(testCategory.id());
        assertEquals(false, deleted.isPresent());
    }

    @Test
    public void Find_all_categories() throws SQLException {
        Iterable<Category> categoryIterable = jdbcCategoryRepository.findAll();
        AtomicInteger j = new AtomicInteger();
        categoryIterable.forEach(category -> {
            testList.forEach(categoryFromList -> {
                if (categoryFromList.id().equals(category.id())) {
                    j.getAndIncrement();
                }
            });
        });
        assertEquals(testList.size(), j.get());
    }

    @Test
    public void Find_pageable_categories() throws SQLException {
        List<Category> allCategories = Lists.newArrayList(jdbcCategoryRepository.findAll());
        int start = 1;
        int size = 3;
        Page<Category> categoryPage = jdbcCategoryRepository.findBy("s0", 'a', start, size);
        List<Category> categoriesFromToList = Lists.newArrayList(categoryPage.items());
        boolean isSame = allCategories.get(0).id().equals(categoriesFromToList.get(0).id())
                && allCategories.get(1).id().equals(categoriesFromToList.get(1).id())
                && allCategories.get(2).id().equals(categoriesFromToList.get(2).id());
        assertEquals(true, isSame);
    }

    @After
    public void deleteTestCategory() throws SQLException {
        jdbcCategoryRepository.remove(testCategory.id());
        testList.forEach(category -> {
            try {
                jdbcCategoryRepository.remove(category.id());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void initTests() throws SQLException {
        testCategory = new Category(UUID.randomUUID(), "TEST_CAT");
        jdbcCategoryRepository.add(testCategory);
        testList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Category liseElement = new Category(UUID.randomUUID(), "!!!!0000" + i + "TEST_CAT");
            jdbcCategoryRepository.add(liseElement);
            testList.add(liseElement);
        }
    }


}
