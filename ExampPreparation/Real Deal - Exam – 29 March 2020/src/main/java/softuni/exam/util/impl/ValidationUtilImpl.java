package softuni.exam.util.impl;

import softuni.exam.util.ValidationUtil;

import javax.validation.Validation;
import javax.validation.Validator;


public class ValidationUtilImpl implements ValidationUtil {
    private final Validator validator;

    public ValidationUtilImpl() {
        this.validator = Validation
                .buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public <T> boolean isValid(T entity) {
        return validator.validate(entity).isEmpty();
    }
}
