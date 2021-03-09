package com.mgolebiewski.recruitment.application.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mgolebiewski.recruitment.application.validation.CurrencyCodeField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentCreationDTO {

  @NotNull
  private BigDecimal amount;

  @CurrencyCodeField
  private String currencyCode;

  @NotNull
  private Long userId;

  @NotNull
  private String targetAccountNumber;

}
