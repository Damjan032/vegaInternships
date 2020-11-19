package rs.vegait.timesheet.jdbc;

import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.project.Project;
import rs.vegait.timesheet.core.model.timesheet.DailyTimeSheet;
import rs.vegait.timesheet.core.model.timesheet.SpentTime;
import rs.vegait.timesheet.core.model.timesheet.TimeSheet;
import rs.vegait.timesheet.core.model.timesheet.TimeSheetDescription;
import rs.vegait.timesheet.core.repository.DailyTimeSheetRepository;
import rs.vegait.timesheet.core.repository.ProjectRepository;
import rs.vegait.timesheet.core.repository.TimeSheetRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcTimeSheetRepository implements TimeSheetRepository {
    private final Connection connection;
    private final ProjectRepository projectRepository;
    private final DailyTimeSheetRepository dailyTimeSheetRepository;
    private final String TABLE_NAME = "timesheets";

    public JdbcTimeSheetRepository(Connection connection, ProjectRepository projectRepository, DailyTimeSheetRepository dailyTimeSheetRepository) {
        this.connection = connection;
        this.projectRepository = projectRepository;
        this.dailyTimeSheetRepository = dailyTimeSheetRepository;
    }

    @Override
    public void add(TimeSheet newObject) throws SQLException {
        String sql = "INSERT INTO `timesheet`.`timesheets` (`id`, `time`, `description`, `overtime`, `projectID`, `dailytimesheetID`) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, newObject.id().toString());
        prepareStatement.setDouble(2, newObject.time().numberOfHours());
        if (newObject.hasDescription()) prepareStatement.setString(3, newObject.description().description());
        else prepareStatement.setNull(3, java.sql.Types.NULL);
        if (newObject.hasDescription()) prepareStatement.setDouble(4, newObject.overtime().numberOfHours());
        else prepareStatement.setNull(4, java.sql.Types.NULL);
        prepareStatement.setString(5, newObject.project().id().toString());
        prepareStatement.setString(6, newObject.dailyTimeSheet().id().toString());

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
    public void update(TimeSheet newObject) throws SQLException {
        Optional<TimeSheet> timeSheetOptional = this.findById(newObject.id());
        if (!timeSheetOptional.isPresent()) {
            return;
        }
        String sql = "UPDATE `timesheets` SET " +
                "`time`=?, " +
                "`description`=?, " +
                "`overtime`=?, " +
                "`projectID`=?, " +
                "`dailytimesheetID`=? " +
                " WHERE (id = ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDouble(1, newObject.time().numberOfHours());
        if (!newObject.hasDescription() && !timeSheetOptional.get().hasDescription()) {
            preparedStatement.setNull(2, Types.NULL);
        } else {
            preparedStatement.setString(2,
                    !newObject.hasDescription() ? timeSheetOptional.get().description().description() : newObject.description().description());
        }
        if (!newObject.hasOvertime() && !timeSheetOptional.get().hasOvertime()) {
            preparedStatement.setNull(3, Types.NULL);
        } else {
            preparedStatement.setDouble(3,
                    !newObject.hasOvertime() ? timeSheetOptional.get().overtime().numberOfHours() : newObject.overtime().numberOfHours());
        }
        preparedStatement.setString(4, newObject.project().id().toString());
        preparedStatement.setString(5, newObject.dailyTimeSheet().id().toString());

        preparedStatement.setString(6, newObject.id().toString());
        preparedStatement.executeUpdate();

    }

    @Override
    public Iterable<TimeSheet> findAll() throws SQLException {
        return null;
    }

    public Iterable<TimeSheet> findDailyTimeSheetsForDailySheet(DailyTimeSheet dailyTimeSheet) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE dailytimesheetID = ?  ";
        List<TimeSheet> timeSheets = new ArrayList<>();

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, dailyTimeSheet.id().toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Project proj = projectRepository.findById(UUID.fromString(rs.getString("projectId"))).get();
            DailyTimeSheet dailyTimeSheetEl = dailyTimeSheetRepository.findById(UUID.fromString(rs.getString("dailytimesheetID"))).get();
            Optional<TimeSheetDescription> timeSheetDescription = Optional.empty();
            if (rs.getString("description") != null)
                timeSheetDescription = Optional.of(new TimeSheetDescription(rs.getString("description")));

            Optional<SpentTime> overTime = Optional.empty();
            if (rs.getString("overtime") != null)
                overTime = Optional.of(new SpentTime(rs.getDouble("overtime")));

            timeSheets.add(new TimeSheet(UUID.fromString(rs.getString("id")),
                    new SpentTime(rs.getDouble("time")),
                    timeSheetDescription,
                    overTime,
                    proj,
                    dailyTimeSheetEl));
        }
        rs.close();
        return timeSheets;
    }

    @Override
    public Optional<TimeSheet> findById(UUID id) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id LIKE(?)";
        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, id.toString());
        TimeSheet timeSheet = null;
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            Project proj = projectRepository.findById(UUID.fromString(rs.getString("projectId"))).get();
            DailyTimeSheet dailyTimeSheet = dailyTimeSheetRepository.findById(UUID.fromString(rs.getString("dailytimesheetID"))).get();
            Optional<TimeSheetDescription> timeSheetDescription = Optional.empty();
            if (rs.getString("description") != null)
                timeSheetDescription = Optional.of(new TimeSheetDescription(rs.getString("description")));

            Optional<SpentTime> overTime = Optional.empty();
            if (rs.getString("overtime") != null)
                overTime = Optional.of(new SpentTime(rs.getDouble("overtime")));

            timeSheet = new TimeSheet(UUID.fromString(rs.getString("id")),
                    new SpentTime(rs.getDouble("time")),
                    timeSheetDescription,
                    overTime,
                    proj,
                    dailyTimeSheet);
        }
        rs.close();
        if (timeSheet == null) {
            return Optional.empty();
        }
        return Optional.of(timeSheet);
    }

    @Override
    public Optional<TimeSheet> findByName(String name) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Page<TimeSheet> findBy(String searchText, char firstLetter, int pageNumber, int pageSize) throws SQLException {
        return null;
    }
}
