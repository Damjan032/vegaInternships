package rs.vegait.timesheet.api.factory;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import rs.vegait.timesheet.api.dto.ClientDto;
import rs.vegait.timesheet.api.dto.PageDto;
import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.client.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class ClientFactory implements Factory<ClientDto, Client> {

    @Override
    public Client createFromDto(UUID id, ClientDto clientDto) {
        List<String> address = Arrays.asList(clientDto.getStreet().trim().split(" "));
        Street clientStreet = new Street("Address", " ");
        String name = address.get(0);
        if (address.size() == 1) {
            clientStreet = new Street(clientDto.getStreet(), " ");
        } else {
            clientStreet = new Street(name.replace(",", ""),
                    (String.join(" ", address.subList(1, address.size()))).replace(",", ""));

        }
        return new Client(
                id,
                new ClientName(clientDto.getName()),
                new Address(
                        clientStreet,
                        new City(clientDto.getCity(), clientDto.getZipCode()),
                        new Country(clientDto.getCountry())
                ));
    }

    @Override
    public ClientDto toDto(Client client) {
        return new ClientDto(
                client.id().toString(),
                client.name(),
                client.address().street().name() + " " + client.address().street().number(),
                client.address().city().name(),
                client.address().city().postalCode(),
                client.address().country().name()
        );
    }

    @Override
    public List<ClientDto> toListDto(Iterable<Client> iterable) {
        List<ClientDto> clientDtoList = new ArrayList<>();
        iterable.forEach(client -> clientDtoList.add(toDto(client)));
        return clientDtoList;
    }

    @Override
    public PageDto<ClientDto> toDtoPage(Page<Client> page) {
        return new PageDto<>(
                toListDto(page.items()),
                page.pageNumber(),
                page.pageSize(),
                page.totalItems()
        );
    }
}
