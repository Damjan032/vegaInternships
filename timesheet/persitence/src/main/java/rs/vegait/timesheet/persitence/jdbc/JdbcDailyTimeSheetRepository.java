package rs.vegait.timesheet.persitence.jdbc;


import org.springframework.stereotype.Component;
import rs.vegait.timesheet.core.model.employee.Employee;
import rs.vegait.timesheet.core.model.project.Category;
import rs.vegait.timesheet.core.model.project.Project;
import rs.vegait.timesheet.core.model.timesheet.DailyTimeSheet;
import rs.vegait.timesheet.core.model.timesheet.SpentTime;
import rs.vegait.timesheet.core.model.timesheet.TimeSheet;
import rs.vegait.timesheet.core.model.timesheet.TimeSheetDescription;
import rs.vegait.timesheet.core.repository.CategoryRepository;
import rs.vegait.timesheet.core.repository.DailyTimeSheetRepository;
import rs.vegait.timesheet.core.repository.EmployeeRepository;
import rs.vegait.timesheet.core.repository.ProjectRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JdbcDailyTimeSheetRepository implements DailyTimeSheetRepository {
    private final String TABLE_NAME = "dailyTimeSheets";
    private final Connection connection;
    private final EmployeeRepository employeeRepository;
    private final CategoryRepository categoryRepository;
    private final ProjectRepository projectRepository;

    public JdbcDailyTimeSheetRepository(Connection connection, EmployeeRepository employeeRepository, CategoryRepository categoryRepository, ProjectRepository projectRepository) {
        this.connection = connection;
        this.employeeRepository = employeeRepository;
        this.categoryRepository = categoryRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public void add(DailyTimeSheet dailyTimeSheet) throws SQLException {
        String sql = "INSERT INTO `timesheet`.`dailyTimeSheets` (`id`, `employeeId`, `day`) " +
                "VALUES (?, ?, ?)";

        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, dailyTimeSheet.id().toString());
        prepareStatement.setString(2, dailyTimeSheet.employee().id().toString());
        prepareStatement.setDate(3, new java.sql.Date(dailyTimeSheet.day().getTime()));

        prepareStatement.executeUpdate();
        for (TimeSheet timeSheet : dailyTimeSheet.timeSheets()) {
            String sqlTimeSheet = "INSERT INTO `timesheet`.`timesheets` ( `time`, `description`, `overtime`, `projectID`, `dailytimesheetID`, `categoryId`) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement prepareStatementTimeSheet = connection.prepareStatement(sqlTimeSheet);
            prepareStatementTimeSheet.setDouble(1, timeSheet.time().numberOfHours());
            if (timeSheet.hasDescription())
                prepareStatementTimeSheet.setString(2, timeSheet.description().description());
            else prepareStatementTimeSheet.setNull(2, java.sql.Types.NULL);
            if (timeSheet.hasDescription())
                prepareStatementTimeSheet.setDouble(3, timeSheet.overtime().numberOfHours());
            else prepareStatementTimeSheet.setNull(3, java.sql.Types.NULL);
            prepareStatementTimeSheet.setString(4, timeSheet.project().id().toString());
            prepareStatementTimeSheet.setString(5, dailyTimeSheet.id().toString());
            prepareStatementTimeSheet.setString(6, timeSheet.category().id().toString());
            prepareStatementTimeSheet.executeUpdate();
        }
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
    public void update(DailyTimeSheet dailyTimeSheet) throws Exception {
        this.remove(dailyTimeSheet.id());
        this.add(dailyTimeSheet);
    }


    @Override
    public Iterable<DailyTimeSheet> findAll() throws Exception {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY day ";
        List<DailyTimeSheet> dailyTimeSheets = new ArrayList<>();

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Employee employee = this.employeeRepository.findById(UUID.fromString(rs.getString("employeeId"))).get();
            long dayTime = rs.getDate("day").getTime();
            Date day = new Date(dayTime);
            dailyTimeSheets.add(new DailyTimeSheet(UUID.fromString(rs.getString("id")), employee, day,
                    this.findDailyTimeSheetsForDailySheet(rs.getString("id"))));
            ;
        }
        rs.close();
        return dailyTimeSheets;
    }

    @Override
    public Iterable<DailyTimeSheet> findDailyTimeSheetsForEmployee(Employee employee, java.util.Date dateFrom, java.util.Date dateTo) throws Exception {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE employeeId = ? AND (day BETWEEN ? AND ?) ORDER BY day ";
        List<DailyTimeSheet> dailyTimeSheets = new ArrayList<>();
        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, employee.id().toString());
        pstmt.setDate(2, new Date(dateFrom.getTime()));
        pstmt.setDate(3, new Date(dateTo.getTime()));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            long dayTime = rs.getDate("day").getTime();
            Date day = new Date(dayTime);
            dailyTimeSheets.add(new DailyTimeSheet(UUID.fromString(rs.getString("id")), employee, day,
                    this.findDailyTimeSheetsForDailySheet(rs.getString("id"))));
        }
        rs.close();
        return dailyTimeSheets;
    }

    @Override
    public Optional<DailyTimeSheet> findByEmployeeAndDay(Employee employee, java.util.Date date) throws Exception {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE employeeId = ? AND day like(?) ";

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, employee.id().toString());
        pstmt.setDate(2, new Date(date.getTime()));
        ResultSet rs = pstmt.executeQuery();
        DailyTimeSheet dailyTimeSheet = null;
        if (rs.next()) {
            long dayTime = rs.getDate("day").getTime();
            Date day = new Date(dayTime);
            dailyTimeSheet = new DailyTimeSheet(UUID.fromString(rs.getString("id")), employee, day,
                    this.findDailyTimeSheetsForDailySheet(rs.getString("id")));
        }
        rs.close();
        if (dailyTimeSheet == null) return Optional.empty();

        return Optional.of(dailyTimeSheet);
    }

    @Override
    public Optional<DailyTimeSheet> findById(UUID id) throws Exception {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id LIKE(?) ORDER BY day, employeeId";
        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, id.toString());
        DailyTimeSheet dailyTimeSheet = null;
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            Employee employee = this.employeeRepository.findById(UUID.fromString(rs.getString("employeeId"))).get();
            long dayTime = rs.getDate("day").getTime();
            Date day = new Date(dayTime);
            dailyTimeSheet = new DailyTimeSheet(UUID.fromString(rs.getString("id")), employee, day,
                    this.findDailyTimeSheetsForDailySheet(rs.getString("id")));

        }
        rs.close();
        if (dailyTimeSheet == null) {
            return Optional.empty();
        }
        return Optional.of(dailyTimeSheet);
    }


    public Iterable<TimeSheet> findDailyTimeSheetsForDailySheet(String dailyTimeSheetId) throws Exception {
        String sql = "SELECT * FROM timesheets WHERE dailytimesheetID = ?  ";
        List<TimeSheet> timeSheets = new ArrayList<>();

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, dailyTimeSheetId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Project proj = this.projectRepository.findById(UUID.fromString(rs.getString("projectId"))).get();
            Optional<TimeSheetDescription> timeSheetDescription = Optional.empty();
            if (rs.getString("description") != null)
                timeSheetDescription = Optional.of(new TimeSheetDescription(rs.getString("description")));

            Optional<SpentTime> overTime = Optional.empty();
            if (rs.getString("overtime") != null)
                overTime = Optional.of(new SpentTime(rs.getDouble("overtime")));
            Optional<Category> category = this.categoryRepository.findById(UUID.fromString(rs.getString("categoryId")));

            timeSheets.add(new TimeSheet(
                    new SpentTime(rs.getDouble("time")),
                    timeSheetDescription,
                    overTime,
                    proj,
                    category.get()));
        }
        rs.close();

        return timeSheets;
    }

}
