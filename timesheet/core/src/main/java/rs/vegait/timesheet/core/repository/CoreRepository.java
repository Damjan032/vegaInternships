package rs.vegait.timesheet.core.repository;


import java.util.Optional;
import java.util.UUID;

public interface CoreRepository<T> {
    void add(T newObject) throws Exception;

    void remove(UUID id) throws Exception;

    void update(T newObject) throws Exception;

    Iterable<T> findAll() throws Exception;

    Optional<T> findById(UUID id) throws Exception;

    // public T save(T newObject); //update if exist like a spring
}
