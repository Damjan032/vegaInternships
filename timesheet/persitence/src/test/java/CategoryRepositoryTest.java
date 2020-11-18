import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
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

public class CategoryRepositoryTest {

    private JdbcCategoryRepository jdbcCategoryRepository;
    private Category testCategory;

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        //String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/timesheet?autoReconnect=true&useSSL=false";
        testCategory = new Category(UUID.randomUUID(), "TEST_CAT");
        //  Database credentials
        String USER = "root";
        String PASS = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        jdbcCategoryRepository = new JdbcCategoryRepository(conn);
        jdbcCategoryRepository.add(testCategory);
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
    public void Find_all_categories() throws  SQLException {
        List<Category> categoryList = new ArrayList<>();
        Category testCategory;
        int i = 0;
        for(i = 0; i<5; i++){
            testCategory = new Category(UUID.randomUUID(), "TEST"+i);
            jdbcCategoryRepository.add(testCategory);
            categoryList.add(testCategory);
        }
        Iterable<Category> categoryIterable = jdbcCategoryRepository.findAll();
        AtomicInteger j = new AtomicInteger();
        categoryIterable.forEach(category -> {
            categoryList.forEach(categoryFromList ->{
                if(categoryFromList.id().equals(category.id())) {
                    j.getAndIncrement();
                }
            });
        });
        for(int k = 0; k<5; k++) jdbcCategoryRepository.remove(categoryList.get(k).id());
        assertEquals(i,j.get());
    }

    @Test
    public void Find_pageable_categories() throws SQLException {
        List<Category> categoryList = new ArrayList<>();
        Category testCategory;
        int i = 0;
        for(i = 0; i<5; i++){
            testCategory = new Category(UUID.randomUUID(), "TEST"+i);
            jdbcCategoryRepository.add(testCategory);
            categoryList.add(testCategory);
        }
        List<Category> allCategories = Lists.newArrayList(jdbcCategoryRepository.findAll());
        int start = 1;
        int size = 3;
        Page<Category>  categoryPage = jdbcCategoryRepository.findBy("s0",'a',start,size);
        List<Category> categoriesFromToList = Lists.newArrayList(categoryPage.items());
        boolean isSame = allCategories.get(1).equals(categoriesFromToList.get(0))
                && allCategories.get(2).equals(categoriesFromToList.get(1))
                && allCategories.get(3).equals(categoriesFromToList.get(2));
        assertEquals(true,isSame);
        for(int k = 0; k<5; k++) jdbcCategoryRepository.remove(categoryList.get(k).id());
    }

    @After
    public void deleteTestCategory() throws SQLException {
        jdbcCategoryRepository.remove(testCategory.id());
    }




}
