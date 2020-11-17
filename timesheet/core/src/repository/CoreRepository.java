package repository;

import model.Page;
import model.client.Client;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public interface CoreRepository<T> {
    public void add(T newObject);

    public void remove(UUID id);

    public void update(T newObject);

    public Iterable<T> findAll();

    public Optional<T> findById(UUID id);

    public Optional<T> findByName(String name);

    public Page<T>  findBy(String searchText, char firstLetter, int pageNumber, int pageSize);

    public void removeByName(String id);

   // public T save(T newObject); //update if exist like a spring
}
