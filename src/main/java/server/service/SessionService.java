package server.service;

import domain.Session;
import repository.IRepository;
import repository.SessionRepository;
import server.validator.IValidator;

public class SessionService extends AbstractService<Integer, Session> {

    private SessionRepository repository;
    private IValidator<Session> validator;

    public SessionService(IValidator<Session> validator, SessionRepository repository) {
        super(validator);
        this.repository = repository;
        this.validator = validator;
    }

    IRepository<Integer, Session> getRepository() { return repository; }
}
