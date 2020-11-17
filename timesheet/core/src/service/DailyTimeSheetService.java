package service;

import model.timesheet.DailyTimeSheet;
import repository.DailyTimeSheetRepository;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class DailyTimeSheetService implements IService<DailyTimeSheet, UUID> {

    private DailyTimeSheetRepository dailyTimeSheetRepository;
    public DailyTimeSheetService(DailyTimeSheetRepository dailyTimeSheetRepository) {
        this.dailyTimeSheetRepository = dailyTimeSheetRepository;
    }

    @Override
    public void create(DailyTimeSheet dailyTimeSheet)  {
        var foundDailyTimeSheet = this.dailyTimeSheetRepository.findById(dailyTimeSheet.id());
        if (foundDailyTimeSheet.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }

        this.dailyTimeSheetRepository.add(dailyTimeSheet);
    }

    @Override
    public Optional<DailyTimeSheet> readById(UUID id) {
        return this.dailyTimeSheetRepository.findById(id);
    }

    @Override
    public void update(DailyTimeSheet updateObject) {
        var foundDailyTimeSheet = this.dailyTimeSheetRepository.findById(updateObject.id());
        if (foundDailyTimeSheet.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }
        this.dailyTimeSheetRepository.update(updateObject);

    }

    @Override
    public void delete(UUID id) {
        var foundDailyTimeSheet = this.dailyTimeSheetRepository.findById(id);
        if (!foundDailyTimeSheet.isPresent()) {
            throw new RuntimeException("Non-existent dailyTimeSheet");
        }
        this.dailyTimeSheetRepository.remove(id);
    }
}
