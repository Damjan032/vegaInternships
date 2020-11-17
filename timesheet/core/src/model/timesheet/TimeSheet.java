package model.timesheet;

import model.project.Project;

public class TimeSheet {
    private SpentTime time;
    private TimeSheetDescription description;
    private SpentTime overtime;
    private Project project;

    public TimeSheet(double time, TimeSheetDescription description, double overtime, Project project) {
        this.time = new SpentTime(time);
        this.description = description;
        this.overtime = new SpentTime(overtime);
        this.project = project;
    }
}
