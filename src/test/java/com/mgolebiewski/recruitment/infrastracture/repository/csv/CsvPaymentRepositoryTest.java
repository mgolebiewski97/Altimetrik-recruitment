package com.mgolebiewski.recruitment.infrastracture.repository.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Currency;
import java.util.Optional;
import java.util.UUID;

import com.mgolebiewski.recruitment.domain.Payment;
import com.mgolebiewski.recruitment.infrastracture.repository.PaymentPersistenceException;
import org.apache.tomcat.util.buf.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvPaymentRepositoryTest {

  private static final Payment MOCK_PAYMENT = Payment.builder()
      .amount(BigDecimal.ONE)
      .currency(Currency.getInstance("USD"))
      .targetAccountNumber("targetaccount")
      .userId(1L)
      .build();

  private static final UUID MOCK_PAYMENT_UUID = UUID.fromString("bb95a9ad-1552-4e83-aee3-3fd4ea825652");


  @Mock
  private File csvFile;

  @Mock
  private WriterFactory writerFactory;

  @Mock
  private ReaderFactory readerFactory;

  @InjectMocks
  private CsvPaymentRepository csvPaymentRepository;

  @Test
  public void shouldSavePaymentRecords() throws IOException {
    StringWriter stringWriter = setupStringWriter();
    when(csvFile.exists()).thenReturn(false);

    csvPaymentRepository.save(MOCK_PAYMENT);

    String resultRow = stringWriter.getBuffer().toString();
    assertFalse(resultRow.isEmpty());
    assertTrue(resultRow.contains(getPaymentRowString(MOCK_PAYMENT, null)));
  }

  @Test
  public void shouldReadPersistedPaymentRecords() throws IOException {
    StringReader stringReader = new StringReader(getMockPaymentsCsv());
    when(readerFactory.getReader(any())).thenReturn(stringReader);

    Collection<Payment> payments = csvPaymentRepository.findAll();

    assertEquals(2, payments.size());
    Payment expectedPayment = MOCK_PAYMENT;
    expectedPayment.setId(MOCK_PAYMENT_UUID);
    assertTrue(payments.contains(expectedPayment));
  }

  @Test
  public void shouldIgnoreCorruptAndInvalidCsvRows() throws IOException {
    String mockPaymentsCSV = "id,amount,currency,userId,targetAccountNumber\n" +
        "invalid-uuid,1,USD,1,targetaccount\n" +
        "261465a7-368c-4c9b-bbad-7e9a06bd3b00,400,PLN,2,targetaccount2\n";
    StringReader stringReader = new StringReader(mockPaymentsCSV);
    when(readerFactory.getReader(any())).thenReturn(stringReader);

    Collection<Payment> payments = csvPaymentRepository.findAll();

    assertEquals(1, payments.size());
  }

  @Test
  public void shouldFindPaymentRowById() throws IOException {
    StringReader stringReader = new StringReader(getMockPaymentsCsv());
    when(readerFactory.getReader(any())).thenReturn(stringReader);

    Optional<Payment> paymentOptional = csvPaymentRepository.findByPaymentId(MOCK_PAYMENT_UUID);

    assertTrue(paymentOptional.isPresent());
    Payment expectedPayment = MOCK_PAYMENT;
    expectedPayment.setId(MOCK_PAYMENT_UUID);
    assertEquals(expectedPayment, paymentOptional.get());
  }

  @Test
  public void shouldUpdatePaymentRow() throws IOException {
    StringReader stringReader = new StringReader(getMockPaymentsCsv());
    StringWriter stringWriter = setupStringWriter();
    when(readerFactory.getReader(any())).thenReturn(stringReader);
    Payment newPayment = Payment.builder()
        .id(MOCK_PAYMENT_UUID)
        .amount(MOCK_PAYMENT.getAmount())
        .currency(MOCK_PAYMENT.getCurrency())
        .targetAccountNumber("new-account#")
        .userId(MOCK_PAYMENT.getUserId())
        .build();

    csvPaymentRepository.save(newPayment);

    String resultRow = stringWriter.getBuffer().toString();
    assertFalse(resultRow.isEmpty());
    assertTrue(resultRow.contains(getPaymentRowString(newPayment, MOCK_PAYMENT_UUID)));
    assertFalse(resultRow.contains(getPaymentRowString(MOCK_PAYMENT, MOCK_PAYMENT_UUID)));
  }

  @Test
  public void shouldThrowExceptionWhenPassedIncompleteData() throws IOException {
    setupStringWriter();

    Payment incompletePayment = Payment.builder()
        .targetAccountNumber("acc")
        .userId(123L)
        .build();

    assertThrows(PaymentPersistenceException.class, () -> csvPaymentRepository.save(incompletePayment));
  }

  private StringWriter setupStringWriter() throws IOException {
    StringWriter stringWriter = new StringWriter();
    BufferedWriter stringBufferedWriter = new BufferedWriter(stringWriter);
    when(writerFactory.getBufferedWriter(any(), any(), any())).thenReturn(stringBufferedWriter);
    return stringWriter;
  }

  private String getPaymentRowString(Payment payment, UUID id) {
    return StringUtils.join(Arrays.asList(
        id == null ? "" : id.toString(),
        payment.getAmount().toString(),
        payment.getCurrency().getCurrencyCode(),
        payment.getUserId().toString(),
        payment.getTargetAccountNumber()
    ), ',');
  }

  private String getMockPaymentsCsv() {
    return "id,amount,currency,userId,targetAccountNumber\n" +
        getPaymentRowString(MOCK_PAYMENT, MOCK_PAYMENT_UUID) + "\n" +
        "261465a7-368c-4c9b-bbad-7e9a06bd3b00,400,PLN,2,targetaccount2\n";
  }
}
