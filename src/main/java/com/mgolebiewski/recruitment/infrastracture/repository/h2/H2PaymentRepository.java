package com.mgolebiewski.recruitment.infrastracture.repository.h2;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import com.mgolebiewski.recruitment.domain.Payment;
import com.mgolebiewski.recruitment.domain.repository.PaymentRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static com.mgolebiewski.recruitment.infrastracture.configuration.CustomProperties.DATASOURCE_IMPL;

@Component
@ConditionalOnProperty(name = DATASOURCE_IMPL, havingValue = "h2") // not implemented, just to show the ease of switching repository impl
public class H2PaymentRepository implements PaymentRepository {
  @Override
  public Payment save(Payment payment) {
    // NOT IMPLEMENTED
    return null;
  }

  @Override
  public Collection<Payment> findAll() {
    // NOT IMPLEMENTED
    return null;
  }

  @Override
  public Optional<Payment> findByPaymentId(UUID paymentId) {
    // NOT IMPLEMENTED
    return null;
  }

  @Override
  public void delete(UUID paymentId) {
    // NOT IMPLEMENTED
  }
}
