package com.mgolebiewski.recruitment.domain.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import com.mgolebiewski.recruitment.domain.Payment;

public interface PaymentRepository {

  Payment save(Payment payment);

  Collection<Payment> findAll();

  Optional<Payment> findByPaymentId(UUID paymentId);

  void delete(UUID paymentId);
}
