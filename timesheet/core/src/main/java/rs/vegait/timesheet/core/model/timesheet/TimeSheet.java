package rs.vegait.timesheet.core.model.timesheet;

import rs.vegait.timesheet.core.model.project.Category;
import rs.vegait.timesheet.core.model.project.Project;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class TimeSheet {
    private final Category category;
    private SpentTime time;
    private Optional<TimeSheetDescription> description;
    private Optional<SpentTime> overtime;
    private Project project;

    public TimeSheet(@NotNull SpentTime time, Optional<TimeSheetDescription> description, Optional<SpentTime> overtime, @NotNull Project project, Category category) {
        this.time = time;
        this.description = description;
        this.overtime = overtime;
        this.project = project;
        this.category = category;
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

    public Project project() {
        return project;
    }

    public Category category() {
        return this.category;
    }

    public TimeSheet changeTimeOfWork(SpentTime time) {
        return new TimeSheet(time, this.description,
                this.overtime, this.project, this.category);
    }

    public TimeSheet makeNewDescription(Optional<TimeSheetDescription> description) {
        return new TimeSheet(this.time, description,
                this.overtime, this.project, this.category);
    }

    public TimeSheet setNewOvertime(Optional<SpentTime> overtime) {
        return new TimeSheet(this.time, this.description,
                overtime, this.project, this.category);
    }

    public TimeSheet changeProject(Project project) {
        return new TimeSheet(this.time, this.description,
                this.overtime, project, this.category);
    }
}
