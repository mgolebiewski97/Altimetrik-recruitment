package com.mgolebiewski.recruitment.domain.service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import com.mgolebiewski.recruitment.domain.Payment;

public interface PaymentService {

  Payment createPayment(Payment payment);

  void updatePayment(Payment payment);

  Collection<Payment> getPayments();

  Optional<Payment> getPaymentById(UUID paymentId);

  void deletePayment(UUID paymentId);
}
