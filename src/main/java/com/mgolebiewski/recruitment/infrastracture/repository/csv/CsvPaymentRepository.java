package com.mgolebiewski.recruitment.infrastracture.repository.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.mgolebiewski.recruitment.domain.Payment;
import com.mgolebiewski.recruitment.infrastracture.repository.PaymentPersistenceException;
import com.mgolebiewski.recruitment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.context.annotation.Primary;

import static com.mgolebiewski.recruitment.infrastracture.configuration.CsvPaymentRepositoryConfig.PAYMENT_CSV_FILE_NAME;


@Slf4j
@Primary
@RequiredArgsConstructor
public class CsvPaymentRepository implements PaymentRepository {

  private static final String[] HEADERS = {"id", "amount", "currency", "userId", "targetAccountNumber"};

  private final CsvPaymentEntityParser csvPaymentEntityParser = new CsvPaymentEntityParser();

  private final File paymentsCsvFile;
  private final WriterFactory writerFactory;
  private final ReaderFactory readerFactory;

  @Override
  public Payment save(Payment payment) {
    return payment.getId() == null ? saveNewPayment(payment) : updatePayment(payment);
  }

  @Override
  public Collection<Payment> findAll() {
    Collection<Payment> payments = new ArrayList<>();

    try (
        Reader reader = readerFactory.getReader(paymentsCsvFile);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader(HEADERS).withSkipHeaderRecord())
    ) {
      csvParser.forEach(csvRecord -> {
        Optional<Payment> paymentOptional = csvPaymentEntityParser.parsePaymentCsvRecord(csvRecord, HEADERS);
        paymentOptional.ifPresent(payments::add);
      });
    } catch (IOException e) {
      log.error("Error occurred while fetching payment records from csv file: {}", PAYMENT_CSV_FILE_NAME, e);
    }

    return payments;
  }

  @Override
  public Optional<Payment> findByPaymentId(UUID paymentId) {
    return findAll().stream()
        .filter(payment -> payment.getId().equals(paymentId))
        .findFirst();
  }

  @Override
  public void delete(UUID paymentId) {
    Collection<Payment> filteredPayments = findAll().stream()
        .filter(payment -> !payment.getId().equals(paymentId))
        .collect(Collectors.toList());

    try {
      rewritePaymentRows(filteredPayments);
    } catch (IOException e) {
      throw new PaymentPersistenceException("Error occurred deleting payment: " + paymentId, e);
    }
  }

  private Payment updatePayment(Payment payment) {
    Collection<Payment> modifiedPayments = findAll().stream()
        .map(fetchedPayment -> fetchedPayment.getId().equals(payment.getId()) ? payment : fetchedPayment)
        .collect(Collectors.toList());

    try {
      rewritePaymentRows(modifiedPayments);
    } catch (IOException e) {
      throw new PaymentPersistenceException("Error occurred while updating payment: " + payment.getId(), e);
    }

    return payment;
  }

  private Payment saveNewPayment(Payment payment) {
    try (
        BufferedWriter writer = writerFactory.getBufferedWriter(paymentsCsvFile, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        CSVPrinter printer = getCSVPrinter(writer)
    ) {
      payment.setId(UUID.randomUUID());
      printer.printRecord(csvPaymentEntityParser.getPaymentRecordFields(payment));
    } catch (IOException e) {
      throw new PaymentPersistenceException("Error occurred while saving payment: " + payment, e);
    }
    return payment;
  }

  private CSVPrinter getCSVPrinter(BufferedWriter writer) throws IOException {
    boolean shouldAppendCsvHeaders = paymentsCsvFile.exists() && paymentsCsvFile.length() == 0;
    CSVFormat csvFormat = shouldAppendCsvHeaders ? CSVFormat.DEFAULT.withHeader(HEADERS) : CSVFormat.DEFAULT;
    return new CSVPrinter(writer, csvFormat);
  }

  private void rewritePaymentRows(Collection<Payment> modifiedPayments) throws IOException {
    try (
        BufferedWriter writer = writerFactory.getBufferedWriter(paymentsCsvFile, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(HEADERS))
    ) {
      printer.printRecords(modifiedPayments.stream()
          .map(csvPaymentEntityParser::getPaymentRecordFields)
          .collect(Collectors.toList()));
    }
  }
}
