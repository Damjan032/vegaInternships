package models.client;

public class Country {
    private int zip;
    private String name;

    public Country(String name, int zip) {
        if (name == null || zip <= 0)
            throw new ExceptionInInitializerError();
    }

    public String name() {
        return name;
    }

    public int zipCode() {
        return zip;
    }
}
