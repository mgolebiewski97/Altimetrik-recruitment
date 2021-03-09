package com.mgolebiewski.recruitment.infrastracture.configuration;

import com.mgolebiewski.recruitment.domain.repository.PaymentRepository;
import com.mgolebiewski.recruitment.domain.service.PaymentService;
import com.mgolebiewski.recruitment.domain.service.PaymentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfig {

  @Bean
  public PaymentService paymentService(PaymentRepository paymentRepository) {
    return new PaymentServiceImpl(paymentRepository);
  }

}
