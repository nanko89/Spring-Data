package softuni.exam.util;

import jdk.jshell.spi.ExecutionControl;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class ValidatorUtilImpl implements ValidatorUtil {

    @Autowired
    private final Validator validator;

    @Autowired
    public ValidatorUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <E> boolean isValid(E entity) {
        if (!validator.validate(entity).isEmpty()) {
            throw new NotYetImplementedException("Not implemented");
        }
        return true;
    }

    @Override
    public <E> Set<ConstraintViolation<E>> violations(E entity) {
        Set<ConstraintViolation<E>> violations = validator.validate(entity);
       if (violations.isEmpty()) {
           throw new NotYetImplementedException("Not implemented");
       }
       return violations;
    }


}
