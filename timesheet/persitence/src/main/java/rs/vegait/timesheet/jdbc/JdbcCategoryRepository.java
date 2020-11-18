package rs.vegait.timesheet.jdbc;

import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.project.Category;
import rs.vegait.timesheet.core.repository.CategoryRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcCategoryRepository implements CategoryRepository {
    private final Connection connection;
    private final String TABLE_NAME = "categories";

    public JdbcCategoryRepository(Connection connection) throws SQLException {
        this.connection = connection;
    }

    @Override
    public void add(Category newObject) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " VALUES ('" +
                newObject.id() + "',  '" + newObject.name() + "')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

    }

    @Override
    public void remove(UUID id) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " " +
                " WHERE id like (?)";

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, id.toString());
        pstmt.execute();

    }

    @Override
    public void update(Category newObject) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " " +
                "SET name = '" + newObject.name() + "'  WHERE id like ('" + newObject.id() + "')";
        //PreparedStatement pstmt = this.connection.prepareStatement(sql);
        //pstmt.setString(1, id.toString());
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

    }

    @Override
    public Iterable<Category> findAll() throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME;
        List<Category> categories = new ArrayList<>();

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            categories.add(new Category(UUID.fromString(rs.getString("id")), rs.getString("name")));
        }
        rs.close();
        return categories;
    }

    @Override
    public Optional<Category> findById(UUID id) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id LIKE(?)";
        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, id.toString());
        Category category = null;

        //Statement statement = connection.createStatement();
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            category = new Category(UUID.fromString(rs.getString("id")), rs.getString("name"));
        }
        rs.close();

        if (category == null) {
            return Optional.empty();
        }
        return Optional.of(category);
    }

    @Override
    public Optional<Category> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name LIKE(?)";
        Category category = null;
        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            category = new Category(UUID.fromString(rs.getString("id")), rs.getString("name"));
        }
        rs.close();


        if (category == null) {
            return Optional.empty();
        }
        return Optional.of(category);
    }

    @Override
    public Page<Category> findBy(String searchText, char firstLetter, int pageNumber, int pageSize) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " LIMIT  ?, ?";
        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setInt(1, pageNumber);
        pstmt.setInt(2, pageSize);
        List<Category> categories = new ArrayList<>();
        int numberOfRows = 0;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS total FROM " + TABLE_NAME);
        rs.next();
        numberOfRows = rs.getInt("total");

        rs = pstmt.executeQuery();
        while (rs.next()) {
            categories.add(new Category(UUID.fromString(rs.getString("id")), rs.getString("name")));
        }
        rs.close();

        Page<Category> categoryPage = new Page<Category>(categories, pageNumber, pageSize, numberOfRows);
        return categoryPage;
    }

    public void removeByName(String name) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " " +
                " WHERE name like (?)";

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.execute();

    }
}
