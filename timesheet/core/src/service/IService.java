package service;

import java.sql.SQLException;
import java.util.Optional;

public interface IService<T, K>{
    public void create(T newObject);

    public Optional<T> readById(K id);

    public void update(T updateObject);

    public void delete(K id);

}
