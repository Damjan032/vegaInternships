package rs.vegait.timesheet.api.factory;

import org.springframework.stereotype.Component;
import rs.vegait.timesheet.api.dto.CountryDto;
import rs.vegait.timesheet.core.model.client.Country;

import java.util.ArrayList;
import java.util.List;

@Component
public class CountryFactory {
    public CountryDto convertToCountryDto(Country country) {
        return new CountryDto(country.name());
    }

    public Iterable<CountryDto> covertListToDtos(Iterable<Country> countries) {
        List<CountryDto> countryDtoList = new ArrayList<>();
        countries.forEach(country -> countryDtoList.add(convertToCountryDto(country)));
        return countryDtoList;
    }
}
