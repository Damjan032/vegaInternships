package rs.vegait.timesheet.jdbc;

import org.springframework.stereotype.Component;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.client.Client;
import rs.vegait.timesheet.core.model.employee.Employee;
import rs.vegait.timesheet.core.model.project.*;
import rs.vegait.timesheet.core.repository.CategoryRepository;
import rs.vegait.timesheet.core.repository.ClientRepository;
import rs.vegait.timesheet.core.repository.EmployeeRepository;
import rs.vegait.timesheet.core.repository.ProjectRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JdbcProjectRepository implements ProjectRepository {
    private final String TABLE_NAME = "projects";
    private final Connection connection;
    private ClientRepository clientRepository;
    private EmployeeRepository employeeRepository;
    private CategoryRepository categoryRepository;

    public JdbcProjectRepository(Connection connection, ClientRepository clientRepository,
                                 EmployeeRepository employeeRepository, CategoryRepository categoryRepository) {
        this.connection = connection;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void add(Project newObject) throws SQLException {
        String sql = "INSERT INTO `timesheet`.`projects` (`id`, `teamleadId`, `name`, `categoryId`, `clientId`, `status`, `description`) " +
                "VALUES (?, ?, " +
                "?, ?," +
                " ?, ?, ?)";

        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, newObject.id().toString());
        prepareStatement.setString(2, newObject.teamLead().id().toString());
        prepareStatement.setString(3, newObject.name().name());
        prepareStatement.setString(4, newObject.category().id().toString());
        prepareStatement.setString(5, newObject.client().id().toString());
        prepareStatement.setString(6, newObject.status().toString());
        if (newObject.hasDescription()) prepareStatement.setString(7, newObject.description().description());
        else prepareStatement.setNull(7, java.sql.Types.NULL);

        prepareStatement.executeUpdate();

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
    public void update(Project newObject) throws Exception {
        Optional<Project> projectOptional = this.findById(newObject.id());
        if (!projectOptional.isPresent()) {
            return;
        }
        String sql = "UPDATE " + TABLE_NAME + " SET " +
                "teamleadId = ?, " +
                "name = ?, " +
                "categoryId = ?, " +
                "clientId = ?, " +
                "status = ?, " +
                "description = ? " +
                "WHERE (id = ?)";

        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        preparedStatement.setString(1,
                newObject.teamLead() == null ? projectOptional.get().teamLead().id().toString() : newObject.teamLead().id().toString());
        preparedStatement.setString(2,
                newObject.name() == null ? projectOptional.get().name().name() : newObject.name().name());
        preparedStatement.setString(3,
                newObject.category() == null ? projectOptional.get().category().id().toString() : newObject.category().id().toString());
        preparedStatement.setString(4,
                newObject.client() == null ? projectOptional.get().client().id().toString() : newObject.client().id().toString());
        preparedStatement.setString(5,
                newObject.status() == null ? projectOptional.get().status().toString() : newObject.status().toString());
        if (!newObject.hasDescription() && !projectOptional.get().hasDescription()) {
            preparedStatement.setNull(6, Types.NULL);
        } else {
            preparedStatement.setString(6,
                    !newObject.hasDescription() ? projectOptional.get().description().description() : newObject.description().description());
        }
        preparedStatement.setString(7, projectOptional.get().id().toString());

        preparedStatement.executeUpdate();

    }

    @Override
    public Iterable<Project> findAll() throws Exception {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY name";
        List<Project> projects = new ArrayList<>();

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Employee employee = this.employeeRepository.findById(UUID.fromString(rs.getString("teamleadId"))).get();
            Client client = this.clientRepository.findById(UUID.fromString(rs.getString("clientId"))).get();
            Category category = this.categoryRepository.findById(UUID.fromString(rs.getString("categoryId"))).get();
            Optional<ProjectDescription> projectDescription = Optional.empty();
            if (rs.getString("description") != null)
                projectDescription = Optional.of(new ProjectDescription(rs.getString("description")));
            projects.add(new Project(
                    UUID.fromString(rs.getString("id")),
                    projectDescription,
                    new ProjectName(rs.getString("name")),
                    ProjectStatus.valueOf(rs.getString("status")),
                    employee,
                    client,
                    category));
        }
        rs.close();
        return projects;
    }

    @Override
    public Optional<Project> findById(UUID id) throws Exception {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id LIKE(?)";
        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, id.toString());

        Project project = null;
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            Employee employee = this.employeeRepository.findById(UUID.fromString(rs.getString("teamleadId"))).get();
            Client client = this.clientRepository.findById(UUID.fromString(rs.getString("clientId"))).get();
            Category category = this.categoryRepository.findById(UUID.fromString(rs.getString("categoryId"))).get();
            Optional<ProjectDescription> projectDescription = Optional.empty();
            if (rs.getString("description") != null)
                projectDescription = Optional.of(new ProjectDescription(rs.getString("description")));
            project = new Project(
                    UUID.fromString(rs.getString("id")),
                    projectDescription,
                    new ProjectName(rs.getString("name")),
                    ProjectStatus.valueOf(rs.getString("status")),
                    employee,
                    client,
                    category);
        }
        rs.close();
        if (project == null) {
            return Optional.empty();
        }
        return Optional.of(project);
    }

    @Override
    public Optional<Project> findByName(String name) throws Exception {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name LIKE(?)";
        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, name);
        Project project = null;
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            Employee employee = this.employeeRepository.findById(UUID.fromString(rs.getString("teamleadId"))).get();
            Client client = this.clientRepository.findById(UUID.fromString(rs.getString("clientId"))).get();
            Category category = this.categoryRepository.findById(UUID.fromString(rs.getString("categoryId"))).get();
            Optional<ProjectDescription> projectDescription = Optional.empty();
            if (rs.getString("description") != null)
                projectDescription = Optional.of(new ProjectDescription(rs.getString("description")));
            project = new Project(
                    UUID.fromString(rs.getString("id")),
                    projectDescription,
                    new ProjectName(rs.getString("name")),
                    ProjectStatus.valueOf(rs.getString("status")),
                    employee,
                    client,
                    category);
        }
        rs.close();
        if (project == null) {
            return Optional.empty();
        }
        return Optional.of(project);
    }

    @Override
    public Page<Project> findBy(String searchText, char firstLetter, int pageNumber, int pageSize) throws Exception {
        String sql = "Select * from projects where ( ?=' ' or name like (?)) " +
                "and name like (?) ORDER BY NAME limit ?,?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);

        preparedStatement.setString(1, String.valueOf(firstLetter));
        preparedStatement.setString(2, String.valueOf(firstLetter) + "%");
        preparedStatement.setString(3, searchText != null ? "%" + searchText.trim() + "%" : "");
        List<Project> projects = new ArrayList<>();
        int numberOfRows = 0;

        PreparedStatement totalRowsStatement = connection.prepareStatement("SELECT COUNT(*) AS total FROM " + TABLE_NAME + " where (? =' ' or name like (?)) " +
                "and name like (?) ORDER BY NAME");
        totalRowsStatement.setString(1, String.valueOf(firstLetter));
        totalRowsStatement.setString(2, String.valueOf(firstLetter) + "%");
        totalRowsStatement.setString(3, searchText != null ? "%" + searchText.trim() + "%" : "");
        ResultSet rs = totalRowsStatement.executeQuery();
        rs.next();
        numberOfRows = rs.getInt("total");

        preparedStatement.setInt(4, (pageNumber - 1) * pageSize);
        preparedStatement.setInt(5, Math.min(numberOfRows, pageNumber * pageSize));

        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Employee employee = this.employeeRepository.findById(UUID.fromString(rs.getString("teamleadId"))).get();
            Client client = this.clientRepository.findById(UUID.fromString(rs.getString("clientId"))).get();
            Category category = this.categoryRepository.findById(UUID.fromString(rs.getString("categoryId"))).get();
            Optional<ProjectDescription> projectDescription = Optional.empty();
            if (rs.getString("description") != null)
                projectDescription = Optional.of(new ProjectDescription(rs.getString("description")));
            projects.add(new Project(
                    UUID.fromString(rs.getString("id")),
                    projectDescription,
                    new ProjectName(rs.getString("name")),
                    ProjectStatus.valueOf(rs.getString("status")),
                    employee,
                    client,
                    category));
        }
        rs.close();

        Page<Project> projectPage = new Page<Project>(projects, pageNumber, pageSize, numberOfRows);
        return projectPage;
    }

}
