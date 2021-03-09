package com.mgolebiewski.recruitment.application.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(PARAMETER)
@Retention(RUNTIME)
@Constraint(validatedBy = UUIDvalidator.class)
public @interface UUIDfield {
  String message() default "Provided UUID is not valid";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
  boolean optional() default false;
}
