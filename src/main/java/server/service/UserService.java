package server.service;

import domain.User;
import repository.IRepository;
import repository.UserRepository;
import server.validator.IValidator;

public class UserService extends AbstractService<Integer, User>{

    private UserRepository repository;
    private IValidator<User> validator;

    public UserService(IValidator<User> validator, UserRepository repository) {
        super(validator);
        this.validator = validator;
        this.repository = repository;
    }

    IRepository<Integer, User> getRepository() {
        return repository;
    }

    public User findUser(User user) {
        return this.repository.findUser(user.getUsername(), user.getPassword());
    }
}