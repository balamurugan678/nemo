package com.novacroft.nemo.common.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Path.Node;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;

public final class Validators {
    static final Logger logger = LoggerFactory.getLogger(Validators.class);

    private Validators() {
    }

    public static boolean hasErrors(String[] properties, BindingResult result, Object object, boolean hasLevel2Validation) {
        boolean hasError = false;
        if (hasLevel2Validation) {
            hasGroupErrors(result, object, Level2Validation.class);
        }
        for (String property : properties) {
            if (result.hasFieldErrors(property)) {
                hasError = true;
                break;
            }
        }
        return hasError;
    }

    public static boolean hasGroupErrors(BindingResult result, Object object, Class<?>... classes) {
        Class<?>[] validatingClasses = classes;
        if (validatingClasses == null || validatingClasses.length == 0 || validatingClasses[0] == null) {
            validatingClasses = new Class<?>[] { Default.class };
        }
        Validator validator = (Validator) Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(object, validatingClasses);

        return hasViolations(violations, result);
    }

    protected static boolean hasViolations(Set<ConstraintViolation<Object>> violations, BindingResult result) {
        for (ConstraintViolation<Object> violation : violations) {
            String propertyName = getPropertyName(violation.getPropertyPath());
            String constraintName = violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
            hasViolation(propertyName, constraintName, violation, result);
        }
        return violations.size() > 0 ? true : false;
    }

    protected static String getPropertyName(Path path) {
        String propertyName = "";
        if (path != null) {
            for (Node n : path) {
                propertyName += n.getName() + ".";
            }
            propertyName = propertyName.substring(0, propertyName.length() - 1);
        }
        return propertyName;
    }

    protected static void hasViolation(String propertyName, String constraintName, ConstraintViolation<Object> violation, BindingResult result) {
        if (propertyName == null || "".equals(propertyName)) {
            result.reject(constraintName, violation.getMessage());
        } else {
            if (result.getFieldErrorCount(propertyName) == 0) {
                result.rejectValue(propertyName, constraintName, violation.getMessage());
            }
        }
    }
}
