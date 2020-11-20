package rs.vegait.timesheet.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.vegait.timesheet.core.model.client.Client;
import rs.vegait.timesheet.core.repository.ClientRepository;
import rs.vegait.timesheet.core.service.ClientService;


@RestController
@RequestMapping(value = "api/clients")
public class ClientsController {
    private final ClientService clientService;
    private final ClientRepository clientRepository;

    public ClientsController(ClientService clientService, ClientRepository clientRepository) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllClients() throws Exception {
        Iterable<Client> clients = clientService.findAll();
        clients.forEach(client -> {
            System.out.println(client.name());
        });
       // List<ClinicRoomDTO> clinicRoomDTOS = clinicRooms.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new ResponseEntity<String >("da", HttpStatus.OK);
    }


}
