package rs.vegait.timesheet.core.service;

import java.sql.SQLException;

public interface BaseService<T, K> {
    void create(T newObject) throws SQLException;

    void update(T updateObject) throws SQLException;

    void delete(K id) throws SQLException;

}
