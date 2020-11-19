package rs.vegait.timesheet.core.service;


import rs.vegait.timesheet.core.model.timesheet.DailyTimeSheet;
import rs.vegait.timesheet.core.repository.DailyTimeSheetRepository;

import java.sql.SQLException;
import java.util.UUID;

public class DailyTimeSheetService implements BaseService<DailyTimeSheet, UUID> {

    private DailyTimeSheetRepository dailyTimeSheetRepository;

    public DailyTimeSheetService(DailyTimeSheetRepository dailyTimeSheetRepository) {
        this.dailyTimeSheetRepository = dailyTimeSheetRepository;
    }

    @Override
    public void create(DailyTimeSheet dailyTimeSheet) throws SQLException {
        var foundDailyTimeSheet = this.dailyTimeSheetRepository.findById(dailyTimeSheet.id());
        if (foundDailyTimeSheet.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }

        this.dailyTimeSheetRepository.add(dailyTimeSheet);
    }


    @Override
    public void update(DailyTimeSheet updateObject) throws SQLException {
        var foundDailyTimeSheet = this.dailyTimeSheetRepository.findById(updateObject.id());
        if (foundDailyTimeSheet.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }
        this.dailyTimeSheetRepository.update(updateObject);

    }

    @Override
    public void delete(UUID id) throws SQLException {
        var foundDailyTimeSheet = this.dailyTimeSheetRepository.findById(id);
        if (!foundDailyTimeSheet.isPresent()) {
            throw new RuntimeException("Non-existent dailyTimeSheet");
        }
        this.dailyTimeSheetRepository.remove(id);
    }
}
