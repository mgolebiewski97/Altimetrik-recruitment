package com.mgolebiewski.recruitment.domain.service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import com.mgolebiewski.recruitment.domain.Payment;
import com.mgolebiewski.recruitment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository paymentRepository;

  @Override
  public Payment createPayment(Payment payment) {
    return paymentRepository.save(payment);
  }

  @Override
  public void updatePayment(Payment payment) {
    paymentRepository.save(payment);
  }

  @Override
  public Collection<Payment> getPayments() {
    return paymentRepository.findAll();
  }

  @Override
  public Optional<Payment> getPaymentById(UUID paymentId) {
    return paymentRepository.findByPaymentId(paymentId);
  }

  @Override
  public void deletePayment(UUID paymentId) {
    paymentRepository.delete(paymentId);
  }
}
