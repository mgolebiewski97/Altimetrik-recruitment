package com.mgolebiewski.recruitment.application.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UUIDvalidatorTest {

  private UUIDvalidator uuiDvalidator = new UUIDvalidator();

  @Test
  public void shouldAllowCorrectUUID() {
    assertTrue(uuiDvalidator.isValid("bb95a9ad-1552-4e83-aee3-3fd4ea825652", null));
  }

  @Test
  public void shouldNotAllowIncorrectUUID() {
    assertFalse(uuiDvalidator.isValid("invalid-uuid", null));
  }

  @Test
  public void shouldNotAllowNullUUID() {
    assertFalse(uuiDvalidator.isValid(null, null));
  }

  @Test
  public void shouldNotAllowEmptyUUID() {
    assertFalse(uuiDvalidator.isValid("", null));
  }
}
