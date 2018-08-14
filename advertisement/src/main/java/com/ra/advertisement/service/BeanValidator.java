package com.ra.advertisement.service;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Component;

@Component("validator")
public class BeanValidator {
    private final transient Validator validator;

    public BeanValidator() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public Validator getValidator() {
        return validator;
    }
}
