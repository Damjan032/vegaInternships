package rs.vegait.timesheet.core.repository;

import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.client.Client;
import rs.vegait.timesheet.core.model.client.Country;

import java.sql.SQLException;

public interface ClientRepository extends CoreRepository<Client> {
    Page<Client> findBy(String s, char c, int pageNumber, int pageSize) throws SQLException;

    Iterable<Country> findCountries() throws SQLException;
}
