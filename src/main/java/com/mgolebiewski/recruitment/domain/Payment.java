package com.mgolebiewski.recruitment.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payment {
  private UUID id;
  private BigDecimal amount;
  private Currency currency;
  private Long userId;
  private String targetAccountNumber;
}
