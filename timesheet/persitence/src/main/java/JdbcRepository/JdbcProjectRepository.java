package JdbcRepository;

import model.Page;
import model.project.Project;
import repository.ProjectRepository;

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
    public void add(Project newObject) {

    }

    @Override
    public void remove(UUID id) {

    }

    @Override
    public void update(Project newObject) {

    }

    @Override
    public Iterable<Project> findAll() {
        return null;
    }

    @Override
    public Optional<Project> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<Project> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Page<Project> findBy(String searchText, char firstLetter, int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public void removeByName(String id) {

    }
}
