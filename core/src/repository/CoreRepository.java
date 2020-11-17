package repository;

import java.util.List;

public interface CoreRepository<T> {
    public List<T> findAll();

    public T findById(String id);

    public T findByName(String name);

    public T removeById(String id);

    public T removeByName(String id);

    public T save(T newObject); //update if exist like a spring
}
