package rs.vegait.timesheet.core.model.client;

public class Address {
    private Street street;
    private City city;
    private Country country;

    public Address(Street street, City city, Country country) {
        if (street == null || city == null || country == null)
            throw new ExceptionInInitializerError();
        this.street = street;
        this.city = city;
        this.country = country;
    }


}
