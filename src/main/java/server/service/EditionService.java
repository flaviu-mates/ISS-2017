package server.service;

import domain.Edition;
import repository.EditionRepository;
import repository.IRepository;
import server.validator.IValidator;

import java.util.Date;
import java.util.List;

public class EditionService extends AbstractService<Integer, Edition> {

    private EditionRepository repository;
    private IValidator<Edition> validator;

    public EditionService(IValidator<Edition> validator, EditionRepository repository) {
        super(validator);
        this.validator = validator;
        this.repository = repository;
    }

    IRepository<Integer, Edition> getRepository() {
        return repository;
    }

    public List<Edition> getEditionAfterDate(Date date) {
        return repository.getEditionAfterDate(date);
    }

}
