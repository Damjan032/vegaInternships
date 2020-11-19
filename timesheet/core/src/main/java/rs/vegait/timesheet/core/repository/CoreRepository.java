package rs.vegait.timesheet.core.repository;


import rs.vegait.timesheet.core.model.Page;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public interface CoreRepository<T> {
    public void add(T newObject) throws SQLException;

    public void remove(UUID id) throws SQLException;

    public void update(T newObject) throws SQLException;

    public Iterable<T> findAll() throws SQLException;

    public Optional<T> findById(UUID id) throws SQLException;

    public Optional<T> findByName(String name) throws SQLException;

    public Page<T> findBy(String searchText, char firstLetter, int pageNumber, int pageSize) throws SQLException;

    // public T save(T newObject); //update if exist like a spring
}
