package rs.vegait.timesheet.api.factory;

import org.springframework.stereotype.Component;
import rs.vegait.timesheet.api.dto.PageDto;
import rs.vegait.timesheet.api.dto.TimeSheetDto;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.timesheet.DailyTimeSheet;
import rs.vegait.timesheet.core.model.timesheet.SpentTime;
import rs.vegait.timesheet.core.model.timesheet.TimeSheet;
import rs.vegait.timesheet.core.model.timesheet.TimeSheetDescription;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TimeSheetFactory {
    private final ProjectFactory projectFactory;
    private final EmployeeFactory employeeFactory;

    public TimeSheetFactory(ProjectFactory projectFactory, EmployeeFactory employeeFactory) {
        this.projectFactory = projectFactory;
        this.employeeFactory = employeeFactory;
    }

    public TimeSheet fromDto(UUID id, TimeSheetDto timeSheetDto, DailyTimeSheet dailyTimeSheet) {
        Optional<TimeSheetDescription> optionalTimeSheetDescription;
        if (timeSheetDto.getDescription() == null || timeSheetDto.getDescription().trim().equalsIgnoreCase(""))
            optionalTimeSheetDescription = Optional.empty();
        else
            optionalTimeSheetDescription = Optional.of(new TimeSheetDescription(timeSheetDto.getDescription()));
        Optional<SpentTime> optionalOvertime;
        if (timeSheetDto.getOvertime() == 0)
            optionalOvertime = Optional.empty();
        else
            optionalOvertime = Optional.of(new SpentTime(timeSheetDto.getOvertime()));
        return new TimeSheet(id, new SpentTime(timeSheetDto.getTime()), optionalTimeSheetDescription,
                optionalOvertime,
                this.projectFactory.createFromDto(UUID.fromString(timeSheetDto.getProjectDto().getId()), timeSheetDto.getProjectDto()),
                dailyTimeSheet);
    }


    public TimeSheetDto toDto(TimeSheet timeSheet) {
        return new TimeSheetDto(timeSheet.id().toString(),
                timeSheet.time().numberOfHours(),
                timeSheet.hasDescription() ? timeSheet.description().description() : null,
                timeSheet.hasOvertime() ? timeSheet.overtime().numberOfHours() : 0,
                this.projectFactory.toDto(timeSheet.project())
        );
    }


    public List<TimeSheetDto> toListDto(Iterable<TimeSheet> iterable) {
        List<TimeSheetDto> timeSheetDtos = new ArrayList<>();
        iterable.forEach(timeSheet -> {
            timeSheetDtos.add(this.toDto(timeSheet));
        });
        return timeSheetDtos;
    }

    public PageDto<TimeSheetDto> toDtoPage(Page<TimeSheet> page) {
        return null;
    }
}
