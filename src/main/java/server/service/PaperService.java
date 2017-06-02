package server.service;

import domain.Paper;
import domain.User;
import repository.IRepository;
import repository.PaperRepository;
import server.validator.IValidator;

import java.util.List;

public class PaperService extends AbstractService<Integer, Paper> {

    private PaperRepository repository;
    private IValidator<Paper> validator;

    public PaperService(IValidator<Paper> validator, PaperRepository repository) {
        super(validator);
        this.validator = validator;
        this.repository = repository;
    }

    IRepository<Integer, Paper> getRepository() {
        return repository;
    }

    public List<Paper> getPapersFromAuthor(User author) {
        return repository.getPapersFromAuthor(author);
    }

}
