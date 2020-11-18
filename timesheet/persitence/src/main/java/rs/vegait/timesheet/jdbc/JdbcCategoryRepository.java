package rs.vegait.timesheet.jdbc;

import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.project.Category;
import rs.vegait.timesheet.core.repository.CategoryRepository;

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
    public JdbcCategoryRepository(Connection connection) throws SQLException {
        this.connection = connection;
        /*statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (id VARCHAR(255) not NULL , " +
                " name VARCHAR(255) not NULL, " +
                " PRIMARY KEY ( id ))");*/
    }
    @Override
    public void add(Category newObject) throws SQLException {
        String sql = "INSERT INTO "+TABLE_NAME+ " VALUES ('" +
                newObject.id() +"',  '"+ newObject.name() +"')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

    }

    @Override
    public void remove(UUID id) throws SQLException {
        String sql = "DELETE FROM "+TABLE_NAME+" " +
                "WHERE id like ('"+ id + "')";

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

    }

    @Override
    public void update(Category newObject) throws SQLException {
        String sql = "UPDATE "+TABLE_NAME+" " +
                "SET name = '"+ newObject.name() +"'  WHERE id like ('"+ newObject.id() + "')";

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

    }

    @Override
    public Iterable<Category> findAll() throws SQLException {
        String sql = "SELECT * FROM "+TABLE_NAME;
        List<Category> categories = new ArrayList<>();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                categories.add(new Category(UUID.fromString(rs.getString("id")),rs.getString("name")));
            }
            rs.close();
        return categories;
    }

    @Override
    public Optional<Category> findById(UUID id) throws SQLException {
        String sql = "SELECT * FROM "+TABLE_NAME + " WHERE id LIKE('"+id+"')";
        Category category = null;

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                category = new Category(UUID.fromString(rs.getString("id")),rs.getString("name"));
            }
            rs.close();

        if (category==null){
            return Optional.empty();
        }
        return Optional.of(category);
    }

    @Override
    public Optional<Category> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM "+TABLE_NAME + " WHERE name LIKE('"+name+"')";
        Category category = null;

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                category = new Category(UUID.fromString(rs.getString("id")),rs.getString("name"));
            }
            rs.close();


        if (category==null){
            return Optional.empty();
        }
        return Optional.of(category);
    }

    @Override
    public Page<Category> findBy(String searchText, char firstLetter, int pageNumber, int pageSize) throws SQLException {
        String sql = "SELECT * FROM "+TABLE_NAME + " LIMIT "+ pageNumber +", " + pageSize;
        List<Category> categories = new ArrayList<>();
        int numberOfRows = 0;

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS total FROM "+TABLE_NAME);
            rs.next();
            numberOfRows = rs.getInt("total");
            rs = statement.executeQuery(sql);
            while(rs.next()){
                categories.add(new Category(UUID.fromString(rs.getString("id")),rs.getString("name")));
            }
            rs.close();

        Page<Category> categoryPage = new Page<Category>(categories,pageNumber,pageSize,numberOfRows);
        return categoryPage;
    }

    @Override
    public void removeByName(String name) throws SQLException {
        String sql = "DELETE FROM "+TABLE_NAME+" " +
                "WHERE name like ('"+ name + "')";

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

    }
}
