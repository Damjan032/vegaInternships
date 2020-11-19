package rs.vegait.timesheet.core.model.client;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) &&
                Objects.equals(city, address.city) &&
                Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, country);
    }

    public Street street() {
        return street;
    }

    public City city() {
        return city;
    }

    public Country country() {
        return country;
    }
}
