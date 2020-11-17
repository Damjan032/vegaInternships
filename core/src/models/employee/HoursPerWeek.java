package models.employee;

public class HoursPerWeek {
    private double hours;

    public HoursPerWeek(double hours) {
        if (hours <= 2) {
            throw new ExceptionInInitializerError();
        }
        this.hours = hours;
    }

    public double hours() {
        return hours;
    }
}
