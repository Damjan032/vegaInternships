package rs.vegait.timesheet.core.service;

import org.springframework.stereotype.Component;
import rs.vegait.timesheet.core.model.timesheet.TimeSheet;
import rs.vegait.timesheet.core.repository.TimeSheetRepository;

import java.util.UUID;

@Component
public class TimeSheetService implements BaseService<TimeSheet, UUID> {
    private final TimeSheetRepository timeSheetRepository;

    public TimeSheetService(TimeSheetRepository timeSheetRepository) {
        this.timeSheetRepository = timeSheetRepository;
    }

    @Override
    public void create(TimeSheet timeSheet) throws Exception {
        var foundTimeSheet = this.timeSheetRepository.findById(timeSheet.id());
        if (foundTimeSheet.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }

        this.timeSheetRepository.add(timeSheet);

    }

    @Override
    public void update(TimeSheet timeSheet) throws Exception {
        var foundTimeSheet = this.timeSheetRepository.findById(timeSheet.id());
        if (foundTimeSheet.isPresent()) {
            throw new RuntimeException("Already exists with same id");
        }
        this.timeSheetRepository.update(timeSheet);

    }

    @Override
    public void delete(UUID id) throws Exception {

    }
}
