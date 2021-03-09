package com.mgolebiewski.recruitment.application.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {

  private String id;

  private BigDecimal amount;

  private String currencyCode;

  private Long userId;

  private String targetAccountNumber;
}
