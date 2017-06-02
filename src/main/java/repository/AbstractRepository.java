package repository;

import domain.HasId;

import java.util.List;

/**
 * Created by Andrei on 5/31/2017.
 */
public class AbstractRepository <ID,T extends HasId<ID>>  implements IRepository<ID,T> {

    protected List<T> entities;

    public AbstractRepository(List<T> entities) {
        this.entities = entities;
    }

    public int size() {
        return entities.size();
    }

    public void save(T entity) {

    }

    public void delete(ID id) {

    }

    public void update(ID id, T entity) {

    }

    public T findOne(ID id) {
        return null;
    }

    public List<T> findAll() {
        return null;
    }
}
