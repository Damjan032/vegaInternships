package rs.vegait.timesheet.core.model.employee;

public class HoursPerWeek {
    private double hours;

    public HoursPerWeek(double hours) {
        if (hours <= 1) {
            throw new IllegalArgumentException("Number of hours of work per week need to be >1" );
        }
        this.hours = hours;
    }

    public double hours() {
        return hours;
    }
}
