package server.service;

import domain.SessionChair;
import repository.IRepository;
import repository.SessionChairRepository;
import server.validator.IValidator;

public class SessionChairService extends AbstractService<Integer, SessionChair>{

    private SessionChairRepository repository;
    private IValidator<SessionChair> validator;

    public SessionChairService(IValidator<SessionChair> validator, SessionChairRepository repository) {
        super(validator);
        this.validator = validator;
        this.repository = repository;
    }

    IRepository<Integer, SessionChair> getRepository() {
        return repository;
    }

}
