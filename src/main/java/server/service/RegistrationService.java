package server.service;

import domain.Registration;
import repository.IRepository;
import repository.RegistrationRepository;
import server.validator.IValidator;

public class RegistrationService extends AbstractService<Integer, Registration>{

    private RegistrationRepository repository;
    private IValidator<Registration> validator;

    public RegistrationService(IValidator<Registration> validator, RegistrationRepository repository) {
        super(validator);
        this.validator = validator;
        this.repository = repository;
    }

    IRepository<Integer, Registration> getRepository() {
        return repository;
    }

}
