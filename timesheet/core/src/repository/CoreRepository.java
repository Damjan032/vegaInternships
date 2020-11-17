package repository;

import models.Page;
import models.client.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CoreRepository<T> {
    public void add(T newObject);

    public void remove(UUID id);

    public void update(T newObject);

    public Iterable<T> findAll();

    public Optional<T> findById(UUID id);

    public Optional<T> findByName(String name);

    public Page<Client>  findBy(String searchText, char firstLetter, int pageNumber, int pageSize);

    public T removeByName(String id);

    public T save(T newObject); //update if exist like a spring
}
