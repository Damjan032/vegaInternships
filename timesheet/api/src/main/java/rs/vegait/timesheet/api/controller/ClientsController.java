package rs.vegait.timesheet.api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.vegait.timesheet.api.dto.ClientDto;
import rs.vegait.timesheet.api.dto.PageDto;
import rs.vegait.timesheet.api.factory.ClientFactory;
import rs.vegait.timesheet.core.model.client.Client;
import rs.vegait.timesheet.core.service.ClientService;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(value = "api/clients")
public class ClientsController {
    private final ClientService clientService;
    private final ClientFactory clientFactory;

    public ClientsController(ClientService clientService, ClientFactory clientFactory) {
        this.clientService = clientService;
        this.clientFactory = clientFactory;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClientDto>> getAll() throws Exception {
        Iterable<Client> employees = clientService.findAll();
        return new ResponseEntity<>(clientFactory.toListDto(employees), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> add(@RequestBody ClientDto clientDto) throws Exception {
        Client newClient = clientFactory.createFromDto(UUID.randomUUID(), clientDto);
        clientService.create(newClient);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("api/clients/" + newClient.id()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(path = {"/{id}"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientDto> update(@PathVariable String id, @RequestBody ClientDto clientDto) throws Exception {
        Client newClient = clientFactory.createFromDto(UUID.fromString(id), clientDto);
        clientService.update(newClient);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }


    @GetMapping(path = "/page")
    public ResponseEntity<PageDto<ClientDto>> getPage(
            @PathParam("pageNumber") Integer pageNumber,
            @PathParam("pageSize") Integer pageSize,
            @PathParam("searchString") String searchString,
            @PathParam("firstLetter") Character firstLetter) throws Exception {

        PageDto<ClientDto> clientDtoPageDto = this.clientFactory.toDtoPage(
                this.clientService.search(pageNumber, pageSize, searchString, firstLetter));
        return new ResponseEntity<>(clientDtoPageDto,
                HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable("id") String id) throws Exception {
        Client client = this.clientService.findById(id);
        return new ResponseEntity<>(this.clientFactory.toDto(client), HttpStatus.OK);
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteClinic(@PathVariable("id") String id) throws Exception {
        this.clientService.delete(UUID.fromString(id));
        return new ResponseEntity<>("deleted client", HttpStatus.OK);
    }
}
