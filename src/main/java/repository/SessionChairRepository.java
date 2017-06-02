package repository;

import domain.SessionChair;
import jdbc.JdbcUtils;

import java.util.List;

public class SessionChairRepository implements IRepository<Integer, SessionChair> {
    private JdbcUtils dbutils;

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(SessionChair entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, SessionChair entity) {

    }

    @Override
    public SessionChair findOne(Integer integer) {
        return null;
    }

    @Override
    public List<SessionChair> findAll() {
        return null;
    }
}
