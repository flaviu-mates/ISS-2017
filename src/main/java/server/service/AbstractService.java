package server.service;

import repository.IRepository;
import server.validator.IValidator;

import java.util.List;

public abstract class AbstractService<ID, T> {
    /**
     * Do not use it directly, call this.validate instead from child class
     */
    private final IValidator validator;

    AbstractService(IValidator validator) {
        this.validator = validator;
    }

    public void add(T entity) throws Exception {
        save(entity);
    }

    public void delete(ID id) {
        getRepository().delete(id);
    }

    /**
     * @param entity The object to save
     * @return The id of the entity
     */
    public void save(T entity) throws Exception {
        validate(entity);
        getRepository().save(entity);
    }

    public List<T> getAll() {
        return getRepository().findAll();
    }

    public T findById(ID key) {
        return getRepository().findOne(key);
    }

    public void update(ID key, T entity) {
        getRepository().update(key, entity);
    }


    void validate(T obj) throws Exception {
        try {
            validator.validate(obj);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    abstract IRepository<ID, T> getRepository();
}
