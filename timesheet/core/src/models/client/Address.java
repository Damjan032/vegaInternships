package models.client;

public class Address {
    private String countryName;
    private Street street;
    private City city;
    private Country country;

    public Address(String countryName, Street street, City city, Country country) {
        if (countryName == null || street== null || city==null || country == null)
            throw new ExceptionInInitializerError();
        this.countryName = countryName;
        this.street = street;
        this.city = city;
        this.country = country;
    }


}
