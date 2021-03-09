package com.mgolebiewski.recruitment.domain.service;

import java.util.UUID;

import com.mgolebiewski.recruitment.domain.Payment;
import com.mgolebiewski.recruitment.domain.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

  @Mock
  private PaymentRepository paymentRepository;

  @InjectMocks
  private PaymentServiceImpl paymentService;

  @Test
  void shouldGenerateUUIDandRequestSave() {
    paymentService.createPayment(Payment.builder().build());

    verify(paymentRepository, times(1)).save(any());
  }

  @Test
  void shouldRequestUpdate() {
    paymentService.updatePayment(Payment.builder().build());

    verify(paymentRepository, times(1)).save(any());
  }

  @Test
  void shouldRequestFetchingSinglePayment() {
    paymentService.getPaymentById(UUID.randomUUID());

    verify(paymentRepository, times(1)).findByPaymentId(any());
  }

  @Test
  void shouldRequestFetchingAllPayments() {
    paymentService.getPayments();

    verify(paymentRepository, times(1)).findAll();
  }

  @Test
  void shouldRequestDeletingPayments() {
    paymentService.deletePayment(UUID.randomUUID());

    verify(paymentRepository, times(1)).delete(any());
  }
}
