package rs.vegait.timesheet.jdbc;

import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.project.Project;
import rs.vegait.timesheet.core.repository.ProjectRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.UUID;

public class JdbcProjectRepository implements ProjectRepository {
    private final String TABLE_NAME = "projects";
    private Statement statement;

    public JdbcProjectRepository(Connection connection) throws SQLException {
        statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (id VARCHAR(255) not NULL , " +
                " teamleadId VARCHAR(255) not NULL, " +
                " name VARCHAR(255) not NULL, " +
                " categoryId VARCHAR(255) not NULL, " +
                " clientId VARCHAR(255) not NULL, " +
                " status INT not NULL, " +
                " description VARCHAR(255), " +
                " PRIMARY KEY ( id )," +
                " FOREIGN KEY (teamleadId) REFERENCES employees(id), " +
                " FOREIGN KEY (clientId) REFERENCES clients(id)," +
                "FOREIGN KEY (categoryId) REFERENCES categories(id))");
    }


    @Override
    public void add(Project newObject) throws SQLException {

    }

    @Override
    public void remove(UUID id) throws SQLException {

    }

    @Override
    public void update(Project newObject) {

    }

    @Override
    public Iterable<Project> findAll() throws SQLException {
        return null;
    }

    @Override
    public Optional<Project> findById(UUID id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Project> findByName(String name) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Page<Project> findBy(String searchText, char firstLetter, int pageNumber, int pageSize) throws SQLException {
        return null;
    }

    @Override
    public void removeByName(String id) throws SQLException {

    }
}
