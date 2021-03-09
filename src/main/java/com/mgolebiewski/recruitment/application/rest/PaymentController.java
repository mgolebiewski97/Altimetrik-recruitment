package com.mgolebiewski.recruitment.application.rest;


import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.mgolebiewski.recruitment.application.dto.PaymentCreationDTO;
import com.mgolebiewski.recruitment.application.dto.PaymentResponseDTO;
import com.mgolebiewski.recruitment.application.mapper.PaymentMapper;
import com.mgolebiewski.recruitment.application.validation.UUIDfield;
import com.mgolebiewski.recruitment.domain.Payment;
import com.mgolebiewski.recruitment.domain.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
@Validated
public class PaymentController {

  private final PaymentService paymentService;
  private final PaymentMapper paymentMapper;

  @PostMapping
  public PaymentResponseDTO createPayment(@RequestBody @Valid PaymentCreationDTO paymentCreationDTO) {
    Payment payment = paymentService.createPayment(paymentMapper.toDomain(paymentCreationDTO));
    return paymentMapper.toDTO(payment);
  }

  @PutMapping("/{paymentIdString}")
  public void updatePayment(
      @PathVariable @UUIDfield String paymentIdString,
      @RequestBody @Valid PaymentCreationDTO paymentCreationDTO
  ) {
    UUID paymentId = UUID.fromString(paymentIdString);
    performIfPaymentExists(paymentId, () -> paymentService.updatePayment(paymentMapper.toDomain(paymentId, paymentCreationDTO)));
  }

  @GetMapping
  public List<PaymentResponseDTO> getPayments() {
    return paymentService.getPayments().stream()
        .map(paymentMapper::toDTO)
        .collect(Collectors.toList());
  }

  @GetMapping("/{paymentIdString}")
  public PaymentResponseDTO getPaymentById(@Valid @PathVariable @UUIDfield String paymentIdString) {
    return paymentMapper.toDTO(paymentService.getPaymentById(UUID.fromString(paymentIdString))
        .orElseThrow(() -> getNotFoundStatusException(paymentIdString)));
  }

  @DeleteMapping("/{paymentIdString}")
  public void deletePayment(@Valid @PathVariable @UUIDfield String paymentIdString) {
    UUID paymentId = UUID.fromString(paymentIdString);
    performIfPaymentExists(paymentId, () -> paymentService.deletePayment(paymentId));
  }

  private void performIfPaymentExists(UUID paymentId, Action action) {
    if (paymentService.getPaymentById(paymentId).isPresent()) {
      action.perform();
    } else {
      throw getNotFoundStatusException(paymentId.toString());
    }
  }

  private ResponseStatusException getNotFoundStatusException(String paymentId) {
    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find payment with id: " + paymentId);
  }
}
