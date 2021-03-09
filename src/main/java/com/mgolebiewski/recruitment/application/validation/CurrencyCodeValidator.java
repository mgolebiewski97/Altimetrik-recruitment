package com.mgolebiewski.recruitment.application.validation;

import java.util.Currency;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.micrometer.core.instrument.util.StringUtils;

public class CurrencyCodeValidator implements ConstraintValidator<CurrencyCodeField, String> {

  @Override
  public void initialize(CurrencyCodeField currencyCodeField) {
  }

  @Override
  public boolean isValid(String currencyCode, ConstraintValidatorContext constraintValidatorContext) {
    try {
      if (StringUtils.isBlank(currencyCode)) {
        return false;
      } else {
        Currency.getInstance(currencyCode);
        return true;
      }
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
