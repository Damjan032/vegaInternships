package models.client;

public class Client {
    private String id;
    private ClientName clientName;
    private ClientAddress address;
    private CityName cityName;
    private Country country;

    public Client(String id, String clientName, String address, String cityName, String countryName, int countryZip) {
        this.id = id;
        this.clientName = new ClientName(clientName);
        this.address = new ClientAddress(address);
        this.cityName = new CityName(cityName);
        this.country = new Country(countryName, countryZip);
    }

    public String id() {
        return id;
    }

    public String name() {
        return clientName.name();
    }

    public String address() {
        return address.address();
    }

    public String cityName() {
        return cityName.name();
    }

    public Country country() {
        return country;
    }

    public int countryZip() {
        return country.zipCode();
    }

    public String countryName() {
        return country.name();
    }
}
