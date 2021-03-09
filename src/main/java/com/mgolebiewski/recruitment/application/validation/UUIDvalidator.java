package com.mgolebiewski.recruitment.application.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.UUID;

import io.micrometer.core.instrument.util.StringUtils;

public class UUIDvalidator implements ConstraintValidator<UUIDfield, String> {

  @Override
  public void initialize(UUIDfield constraintAnnotation) {
  }

  @Override
  public boolean isValid(String uuidString, ConstraintValidatorContext constraintValidatorContext) {
    try {
      if (StringUtils.isBlank(uuidString)) {
        return false;
      } else {
        UUID.fromString(uuidString);
        return true;
      }
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
