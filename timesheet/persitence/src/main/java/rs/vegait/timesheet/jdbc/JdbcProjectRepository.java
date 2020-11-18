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
    private final Connection connection;

    public JdbcProjectRepository(Connection connection) throws SQLException {
        this.connection = connection;
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

}
