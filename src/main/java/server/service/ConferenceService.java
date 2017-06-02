package server.service;

import domain.Conference;
import repository.ConferenceRepository;
import repository.IRepository;
import server.validator.IValidator;

public class ConferenceService extends AbstractService<Integer, Conference> {

    private ConferenceRepository repository;
    private IValidator<Conference> validator;

    public ConferenceService(IValidator<Conference> validator, ConferenceRepository repository) {
        super(validator);
        this.validator = validator;
        this.repository = repository;
    }

    IRepository<Integer, Conference> getRepository() {
        return repository;
    }

}
