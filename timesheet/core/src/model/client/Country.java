package model.client;

public class Country {
    private String name;

    public Country(String name) {
        if (name == null)
            throw new ExceptionInInitializerError();
    }

    public String name() {
        return name;
    }

}
