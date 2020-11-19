package rs.vegait.timesheet.core.model.timesheet;

public class SpentTime {
    private double numberOfHours;

    public SpentTime(double numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    public double numberOfHours() {
        return numberOfHours;
    }
}
