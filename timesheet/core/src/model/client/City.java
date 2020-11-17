package model.client;

public class City {
    private String name;
    private int postalCode;

    public City(String name, int postalCode) {
        if (name == null || postalCode<=10)
            throw new ExceptionInInitializerError();
        this.name = name;
        this.postalCode = postalCode;
    }

    public String name() {
        return name;
    }

    public int postalCode() {
        return postalCode;
    }
}
