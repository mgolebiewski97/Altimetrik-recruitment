package com.mgolebiewski.recruitment.application.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyCodeValidatorTest {

  private CurrencyCodeValidator currencyCodeValidator = new CurrencyCodeValidator();

  @Test
  public void shouldAllowExistingCurrencyCode() {
    assertTrue(currencyCodeValidator.isValid("PLN", null));
  }

  @Test
  public void shouldNotAllowNotExistingCurrencyCode() {
    assertFalse(currencyCodeValidator.isValid("WRONG_CODE", null));
  }

  @Test
  public void shouldNotAllowEmptyCurrencyCode() {
    assertFalse(currencyCodeValidator.isValid("", null));
  }

  @Test
  public void shouldNotAllowNullCurrencyCode() {
    assertFalse(currencyCodeValidator.isValid(null, null));
  }

}
