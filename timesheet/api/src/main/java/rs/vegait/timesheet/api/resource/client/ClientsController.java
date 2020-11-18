package rs.vegait.timesheet.api.resource.client;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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


}
