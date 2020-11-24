package rs.vegait.timesheet.api.factory;

import org.springframework.stereotype.Component;
import rs.vegait.timesheet.api.dto.PageDto;
import rs.vegait.timesheet.api.dto.TimeSheetDto;
import rs.vegait.timesheet.core.model.Page;
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
    private final CategoryFactory categoryFactory;

    public TimeSheetFactory(ProjectFactory projectFactory, CategoryFactory categoryFactory) {
        this.projectFactory = projectFactory;
        this.categoryFactory = categoryFactory;
    }

    public TimeSheet fromDto(TimeSheetDto timeSheetDto) {
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
        return new TimeSheet(new SpentTime(timeSheetDto.getTime()), optionalTimeSheetDescription,
                optionalOvertime,
                this.projectFactory.createFromDto(UUID.fromString(timeSheetDto.getProject().getId()),
                        timeSheetDto.getProject()),
                this.categoryFactory.createFromDto(
                        UUID.fromString(timeSheetDto.getCategoryDto().getId()), timeSheetDto.getCategoryDto()));
    }


    public TimeSheetDto toDto(TimeSheet timeSheet) {
        return new TimeSheetDto(
                timeSheet.time().numberOfHours(),
                timeSheet.hasDescription() ? timeSheet.description().description() : null,
                timeSheet.hasOvertime() ? timeSheet.overtime().numberOfHours() : 0,
                this.projectFactory.toDto(timeSheet.project()),
                this.categoryFactory.toDto(timeSheet.category()));

    }


    public List<TimeSheetDto> toListDto(Iterable<TimeSheet> iterable) {
        List<TimeSheetDto> timeSheetDtos = new ArrayList<>();
        iterable.forEach(timeSheet -> {
            timeSheetDtos.add(this.toDto(timeSheet));
        });
        return timeSheetDtos;
    }

    public List<TimeSheet> toList(Iterable<TimeSheetDto> timeSheetDtos) {
        List<TimeSheet> timeSheets = new ArrayList<>();
        timeSheetDtos.forEach(timeSheetDto -> {
            timeSheets.add(this.fromDto(timeSheetDto));
        });
        return timeSheets;
    }

    public PageDto<TimeSheetDto> toDtoPage(Page<TimeSheet> page) {
        return null;
    }
}
