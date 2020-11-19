package rs.vegait.timesheet.core.model.timesheet;


import rs.vegait.timesheet.core.model.project.Project;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

public class TimeSheet {
    private UUID id;
    private SpentTime time;
    private Optional<TimeSheetDescription> description;
    private Optional<SpentTime> overtime;
    private DailyTimeSheet dailyTimeSheet;
    private Project project;

    public TimeSheet(@NotNull UUID id, @NotNull SpentTime time, Optional<TimeSheetDescription> description, Optional<SpentTime> overtime, @NotNull Project project, @NotNull DailyTimeSheet dailyTimeSheet) {
        this.id = id;
        this.time = time;
        this.description = description;
        this.overtime = overtime;
        this.project = project;
        this.dailyTimeSheet = dailyTimeSheet;
    }

    public SpentTime time() {
        return time;
    }

    public boolean hasDescription() {
        return description.isPresent();
    }

    public TimeSheetDescription description() {
        if (!description.isPresent()) {
            return null;
        }
        return description.get();
    }

    public boolean hasOvertime() {
        return overtime.isPresent();
    }

    public SpentTime overtime() {
        if (!overtime.isPresent()) {
            return null;
        }
        return overtime.get();
    }

    public DailyTimeSheet dailyTimeSheet() {
        return dailyTimeSheet;
    }

    public Project project() {
        return project;
    }

    public UUID id() {
        return id;
    }

    public TimeSheet changeTimeOfWork(SpentTime time) {
        return new TimeSheet(this.id, time, this.description,
                this.overtime, this.project, this.dailyTimeSheet);
    }

    public TimeSheet makeNewDescription(Optional<TimeSheetDescription> description) {
        return new TimeSheet(this.id, this.time, description,
                this.overtime, this.project, this.dailyTimeSheet);
    }

    public TimeSheet setNewOvertime(Optional<SpentTime> overtime) {
        return new TimeSheet(this.id, this.time, this.description,
                overtime, this.project, this.dailyTimeSheet);
    }

    public TimeSheet changeDailyTimeSheet(DailyTimeSheet dailyTimeSheet) {
        return new TimeSheet(this.id, this.time, this.description,
                this.overtime, this.project, dailyTimeSheet);
    }

    public TimeSheet changeProject(Project project) {
        return new TimeSheet(this.id, this.time, this.description,
                this.overtime, project, this.dailyTimeSheet);
    }
}
