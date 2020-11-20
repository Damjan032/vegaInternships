package rs.vegait.timesheet.core.repository;


import rs.vegait.timesheet.core.model.Page;

import java.util.Optional;
import java.util.UUID;

public interface CoreRepository<T> {
    void add(T newObject) throws Exception;

    void remove(UUID id) throws Exception;

    void update(T newObject) throws Exception;

    Iterable<T> findAll() throws Exception;

    Optional<T> findById(UUID id) throws Exception;

    Optional<T> findByName(String name) throws Exception;

    Page<T> findBy(String searchText, char firstLetter, int pageNumber, int pageSize) throws Exception;

    // public T save(T newObject); //update if exist like a spring
}
