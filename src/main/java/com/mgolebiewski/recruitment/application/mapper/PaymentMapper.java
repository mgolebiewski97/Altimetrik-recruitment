package com.mgolebiewski.recruitment.application.mapper;

import java.util.Currency;
import java.util.UUID;

import com.mgolebiewski.recruitment.application.dto.PaymentCreationDTO;
import com.mgolebiewski.recruitment.application.dto.PaymentResponseDTO;
import com.mgolebiewski.recruitment.domain.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

  public Payment toDomain(UUID paymentId, PaymentCreationDTO paymentCreationDTO) {
    Payment payment = toDomain(paymentCreationDTO);
    payment.setId(paymentId);
    return payment;
  }

  public Payment toDomain(PaymentCreationDTO paymentCreationDTO) {
    return Payment.builder()
        .amount(paymentCreationDTO.getAmount())
        .currency(Currency.getInstance(paymentCreationDTO.getCurrencyCode()))
        .userId(paymentCreationDTO.getUserId())
        .targetAccountNumber(paymentCreationDTO.getTargetAccountNumber())
        .build();
  }

  public PaymentResponseDTO toDTO(Payment payment) {
    return new PaymentResponseDTO(
        payment.getId().toString(),
        payment.getAmount(),
        payment.getCurrency().getCurrencyCode(),
        payment.getUserId(),
        payment.getTargetAccountNumber());
  }
}
