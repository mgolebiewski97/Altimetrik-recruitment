package com.mgolebiewski.recruitment.infrastracture.repository.csv;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mgolebiewski.recruitment.domain.Payment;
import com.mgolebiewski.recruitment.infrastracture.repository.PaymentPersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;

@Slf4j
public class CsvPaymentEntityParser {
  Optional<Payment> parsePaymentCsvRecord(CSVRecord csvRecord, String[] headers) {
    try {
      return Optional.of(Payment.builder()
          .id(UUID.fromString(csvRecord.get(headers[0])))
          .amount(new BigDecimal(csvRecord.get(headers[1])))
          .currency(Currency.getInstance(csvRecord.get(headers[2])))
          .userId(Long.valueOf(csvRecord.get(headers[3])))
          .targetAccountNumber(csvRecord.get(headers[4]))
          .build());
    } catch (Exception e) {
      log.error("Failed to parse payments CSV record: {}", csvRecord, e);
      return Optional.empty();
    }
  }

  List<String> getPaymentRecordFields(Payment payment) {
    try {
      return Arrays.asList(payment.getId().toString(),
          payment.getAmount().toString(),
          payment.getCurrency().getCurrencyCode(),
          payment.getUserId().toString(),
          payment.getTargetAccountNumber());
    } catch (Exception e) {
      throw new PaymentPersistenceException("Failed to parse payment to a CSV row, invalid or incomplete data", e);
    }
  }
}
