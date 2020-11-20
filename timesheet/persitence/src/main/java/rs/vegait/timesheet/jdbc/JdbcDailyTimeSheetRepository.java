package rs.vegait.timesheet.jdbc;


import org.springframework.stereotype.Component;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.employee.Employee;
import rs.vegait.timesheet.core.model.timesheet.DailyTimeSheet;
import rs.vegait.timesheet.core.repository.DailyTimeSheetRepository;
import rs.vegait.timesheet.core.repository.EmployeeRepository;

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

    public JdbcDailyTimeSheetRepository(Connection connection, EmployeeRepository employeeRepository) throws SQLException {
        this.connection = connection;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void add(DailyTimeSheet newObject) throws SQLException {
        String sql = "INSERT INTO `timesheet`.`dailyTimeSheets` (`id`, `employeeId`, `day`) " +
                "VALUES (?, ?, ?)";

        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, newObject.id().toString());
        prepareStatement.setString(2, newObject.employee().id().toString());
        prepareStatement.setDate(3, new java.sql.Date(newObject.day().getTime()));

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
    public Iterable<DailyTimeSheet> findAll() throws Exception {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY day ";
        List<DailyTimeSheet> dailyTimeSheets = new ArrayList<>();

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Employee employee = this.employeeRepository.findById(UUID.fromString(rs.getString("employeeId"))).get();
            long dayTime = rs.getDate("day").getTime();
            Date day = new Date(dayTime);
            dailyTimeSheets.add(new DailyTimeSheet(UUID.fromString(rs.getString("id")), employee, day));
            ;
        }
        rs.close();
        return dailyTimeSheets;
    }

    @Override
    public Iterable<DailyTimeSheet> findDailyTimeSheetsForEmployer(Employee employee) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE employeeId = ? ORDER BY day ";
        List<DailyTimeSheet> dailyTimeSheets = new ArrayList<>();

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        pstmt.setString(1, employee.id().toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            long dayTime = rs.getDate("day").getTime();
            Date day = new Date(dayTime);
            System.out.println(day);
            dailyTimeSheets.add(new DailyTimeSheet(UUID.fromString(rs.getString("id")), employee, day));
            ;
        }
        rs.close();
        return dailyTimeSheets;
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
            dailyTimeSheet = new DailyTimeSheet(UUID.fromString(rs.getString("id")), employee, day);

        }
        rs.close();
        if (dailyTimeSheet == null) {
            return Optional.empty();
        }
        return Optional.of(dailyTimeSheet);
    }

    @Override
    public Optional<DailyTimeSheet> findByName(String name) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Page<DailyTimeSheet> findBy(String searchText, char firstLetter, int pageNumber, int pageSize) throws SQLException {
        return null;
    }

    @Override
    public void update(DailyTimeSheet newObject) {

    }

}
