package rs.vegait.timesheet.jdbc;



import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.timesheet.DailyTimeSheet;
import rs.vegait.timesheet.core.repository.DailyTimeSheetRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.UUID;

public class JdbcDailyTimeSheetRepository implements DailyTimeSheetRepository {
    private final String TABLE_NAME = "dailyTimeSheets";
    private final Connection connection;

    public JdbcDailyTimeSheetRepository(Connection connection) throws SQLException {
        this.connection = connection;
    }

    @Override
    public void add(DailyTimeSheet newObject) throws SQLException {

    }

    @Override
    public void remove(UUID id) throws SQLException {

    }

    @Override
    public void update(DailyTimeSheet newObject) {

    }

    @Override
    public Iterable<DailyTimeSheet> findAll() throws SQLException {
        return null;
    }

    @Override
    public Optional<DailyTimeSheet> findById(UUID id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<DailyTimeSheet> findByName(String name) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Page<DailyTimeSheet> findBy(String searchText, char firstLetter, int pageNumber, int pageSize) throws SQLException {
        return null;
    }

}
