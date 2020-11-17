package model.client;

public class Street {
    private String name;
    private String number;

    public Street(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String name() {
        return name;
    }

    public String number() {
        return number;
    }
}
