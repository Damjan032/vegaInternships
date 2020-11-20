package rs.vegait.timesheet.core.service;

import java.sql.SQLException;

public interface BaseService<T, K> {
    void create(T newObject) throws Exception;

    void update(T updateObject) throws Exception;

    void delete(K id) throws Exception;

}
