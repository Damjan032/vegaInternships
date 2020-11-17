

package JdbcRepository;

import model.Page;
import model.project.Category;
import repository.CategoryRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcCategoryRepository implements CategoryRepository {
    private final Connection connection;
    private final String TABLE_NAME = "categories";
    private Statement statement;

    public JdbcCategoryRepository(Connection connection) throws SQLException {
        this.connection = connection;
        statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (id VARCHAR(255) not NULL , " +
                " name VARCHAR(255) not NULL, " +
                " PRIMARY KEY ( id ))");
    }
    @Override
    public void add(Category newObject)  {
        String sql = "INSERT INTO "+TABLE_NAME+ " VALUES ('" +
                newObject.id() +"',  '"+ newObject.name() +"')";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void remove(UUID id) {
        String sql = "DELETE FROM "+TABLE_NAME+" " +
                "WHERE id like ('"+ id + "')";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Category newObject) {
        String sql = "UPDATE "+TABLE_NAME+" " +
                "SET name = '"+ newObject.name() +"'  WHERE id like ('"+ newObject.id() + "')";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Iterable<Category> findAll() {
        String sql = "SELECT * FROM "+TABLE_NAME;
        List<Category> categories = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                categories.add(new Category(UUID.fromString(rs.getString("id")),rs.getString("name")));
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return categories;
    }

    @Override
    public Optional<Category> findById(UUID id) {
        String sql = "SELECT * FROM "+TABLE_NAME + " WHERE id LIKE('"+id+"')";
        Category category = null;
        try {
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                category = new Category(UUID.fromString(rs.getString("id")),rs.getString("name"));
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.of(category);
    }

    @Override
    public Optional<Category> findByName(String name) {
        String sql = "SELECT * FROM "+TABLE_NAME + " WHERE name LIKE('"+name+"')";
        Category category = null;
        try {
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                category = new Category(UUID.fromString(rs.getString("id")),rs.getString("name"));
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.of(category);
    }

    @Override
    public Page<Category> findBy(String searchText, char firstLetter, int pageNumber, int pageSize) {
        String sql = "SELECT * FROM "+TABLE_NAME + " LIMIT "+ pageNumber +", " + pageSize;
        List<Category> categories = new ArrayList<>();
        int numberOfRows = 0;
        try {
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS total FROM "+TABLE_NAME);
            rs.next();
            numberOfRows = rs.getInt("total");
            rs = statement.executeQuery(sql);
            while(rs.next()){
                categories.add(new Category(UUID.fromString(rs.getString("id")),rs.getString("name")));
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Page<Category> categoryPage = new Page<Category>(categories,pageNumber,pageSize,numberOfRows);
        return categoryPage;
    }

    @Override
    public void removeByName(String name) {
        String sql = "DELETE FROM "+TABLE_NAME+" " +
                "WHERE name like ('"+ name + "')";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
