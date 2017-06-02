package repository;

import java.util.List;

/**
 * Created by Andrei on 5/31/2017.
 */
public interface IRepository <ID,T> {
    int size();
    void save(T entity);
    void delete(ID id);
    void update(ID id, T entity);
    T findOne(ID id);
    List<T> findAll();

}