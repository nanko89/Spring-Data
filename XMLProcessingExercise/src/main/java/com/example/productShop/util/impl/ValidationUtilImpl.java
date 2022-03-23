package com.example.productShop.util.impl;

import com.example.productShop.util.ValidationUtil;
import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;

@Component
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
