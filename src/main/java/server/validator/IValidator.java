package server.validator;

public interface IValidator<E> {
    void validate(E entity) throws ValidatorException;
}
