
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rs.vegait.timesheet.core.model.project.Category;
import rs.vegait.timesheet.jdbc.JdbcCategoryRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

public class CategoryRepositoryTest {

    private JdbcCategoryRepository jdbcCategoryRepository;
    private Category testCategory;

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        //String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/timesheet?autoReconnect=true&useSSL=false";
        testCategory = new Category(UUID.randomUUID(),"TEST_CAT");
        //  Database credentials
        String USER = "root";
        String PASS = "root";
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        jdbcCategoryRepository = new JdbcCategoryRepository(conn);
        jdbcCategoryRepository.add(testCategory);
    }

    @Test
    public void addAndFindByIdTrue() throws SQLException {
        Optional<Category> result = jdbcCategoryRepository.findById(testCategory.id());
        assertEquals(true, result.isPresent());
    }

    @Test
    public void Find_category_by_existing_name() throws SQLException {
        Optional<Category> category = jdbcCategoryRepository.findByName(testCategory.name());
        assertEquals(testCategory,category.get());
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
        System.out.println(testCategory.name());
        jdbcCategoryRepository.update(testCategory);
        Optional<Category> result = jdbcCategoryRepository.findByName(newName);
        assertEquals(true, result.isPresent());
    }

    @Test
    public void removeById() throws SQLException {
        jdbcCategoryRepository.remove(testCategory.id());
        Optional<Category> deleted = jdbcCategoryRepository.findById(testCategory.id());
        assertEquals(false, deleted.isPresent());
    }

    @Test
    public void removeByName() throws SQLException {
        jdbcCategoryRepository.removeByName(testCategory.name());
        Optional<Category> deleted = jdbcCategoryRepository.findById(testCategory.id());
        assertEquals(false, deleted.isPresent());
    }

    @After
    public void deleteTestCategory() throws SQLException {
        jdbcCategoryRepository.remove(testCategory.id());
    }



}
