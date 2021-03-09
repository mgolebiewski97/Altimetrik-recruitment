package com.mgolebiewski.recruitment.infrastracture.configuration;

import java.io.File;

import com.mgolebiewski.recruitment.domain.repository.PaymentRepository;
import com.mgolebiewski.recruitment.infrastracture.repository.csv.CsvPaymentRepository;
import com.mgolebiewski.recruitment.infrastracture.repository.csv.ReaderFactory;
import com.mgolebiewski.recruitment.infrastracture.repository.csv.WriterFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.mgolebiewski.recruitment.infrastracture.configuration.CustomProperties.DATASOURCE_IMPL;

@Configuration
@ConditionalOnProperty(name = DATASOURCE_IMPL, havingValue = "csv")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class CsvPaymentRepositoryConfig {

  public static final String PAYMENT_CSV_FILE_NAME = "payments.csv";

  @Bean
  public PaymentRepository csvPaymentRepository() {
    return new CsvPaymentRepository(new File(PAYMENT_CSV_FILE_NAME), new WriterFactory(), new ReaderFactory());
  }
}
