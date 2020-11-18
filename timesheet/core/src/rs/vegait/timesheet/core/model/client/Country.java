package rs.vegait.timesheet.core.model.client;

public class Country {
    private String name;

    public Country(String name) {
        if (name == null)
            throw new ExceptionInInitializerError();
        this.name = name;
    }

    public String name() {
        return name;
    }

}
