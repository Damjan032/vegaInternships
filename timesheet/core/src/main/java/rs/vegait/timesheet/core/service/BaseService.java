package rs.vegait.timesheet.core.service;

public interface BaseService<T, K> {
    void create(T newObject) throws Exception;

    void update(T updateObject) throws Exception;

    void delete(K id) throws Exception;

}
