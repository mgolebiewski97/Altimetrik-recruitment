package com.mgolebiewski.recruitment.application.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = CurrencyCodeValidator.class)
public @interface CurrencyCodeField {
  String message() default "Currency with this code does not exist";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
  boolean optional() default false;
}
