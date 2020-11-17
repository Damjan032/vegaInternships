package models.client;

public class CityName {
    private String name;

    public CityName(String name) {
        if (name == null)
            throw new ExceptionInInitializerError();
    }

    public String name() {
        return name;
    }
}
