package domain.validators;

public interface Validator<T> {
    void validate(T networkEntity, ValidatorStrategy strategy) throws ValidatorException;
}
