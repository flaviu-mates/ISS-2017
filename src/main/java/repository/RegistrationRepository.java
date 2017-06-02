package repository;

import domain.Registration;
import jdbc.JdbcUtils;

import java.util.List;

public class RegistrationRepository implements IRepository<Integer, Registration> {
    private JdbcUtils dbutils;

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(Registration entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Registration entity) {

    }

    @Override
    public Registration findOne(Integer integer) {
        return null;
    }

    @Override
    public List<Registration> findAll() {
        return null;
    }
}
