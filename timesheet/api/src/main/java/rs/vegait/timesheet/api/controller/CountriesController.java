package rs.vegait.timesheet.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.vegait.timesheet.api.dto.CountryDto;
import rs.vegait.timesheet.api.factory.CountryFactory;
import rs.vegait.timesheet.core.repository.ClientRepository;

@RestController
@RequestMapping(value = "api/countries")
public class CountriesController {

    private final ClientRepository clientRepository;
    private final CountryFactory countriesFactory;

    public CountriesController(ClientRepository clientRepository, CountryFactory countriesFactory) {
        this.clientRepository = clientRepository;
        this.countriesFactory = countriesFactory;
    }

    @GetMapping()
    public ResponseEntity<Iterable<CountryDto>> getCountriesAll() throws Exception {
        return new ResponseEntity<>(this.countriesFactory.covertListToDtos(this.clientRepository.findCountries()), HttpStatus.OK);
    }
}
