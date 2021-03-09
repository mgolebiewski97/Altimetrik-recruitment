package com.mgolebiewski.recruitment.infrastracture.repository;

public class PaymentPersistenceException extends RuntimeException {
  public PaymentPersistenceException(String message, Exception e) {
    super(message, e);
  }
}
