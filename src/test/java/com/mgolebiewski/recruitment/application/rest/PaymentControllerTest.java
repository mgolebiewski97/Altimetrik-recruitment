package com.mgolebiewski.recruitment.application.rest;

import java.math.BigDecimal;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgolebiewski.recruitment.application.dto.PaymentCreationDTO;
import com.mgolebiewski.recruitment.application.mapper.PaymentMapper;
import com.mgolebiewski.recruitment.domain.Payment;
import com.mgolebiewski.recruitment.domain.service.PaymentService;
import jdk.jfr.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PaymentController.class)
class PaymentControllerIntegrationTest {

  private static final PaymentCreationDTO MOCK_CREATION_PAYMENT_VALID = new PaymentCreationDTO(new BigDecimal(10), "USD", 123L, "account");
  private static final PaymentCreationDTO MOCK_CREATION_PAYMENT_INVALID = new PaymentCreationDTO(new BigDecimal(10), "INVALID", 123L, "account");

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private PaymentMapper paymentMapper;

  @MockBean
  private PaymentService paymentService;

  @Test
  public void shouldReturn200OnValidPost() throws Exception {
    mockMvc.perform(post("/api/payment")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(MOCK_CREATION_PAYMENT_VALID)))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldReturn400OnInvalidPost() throws Exception {
    mockMvc.perform(post("/api/payment")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(MOCK_CREATION_PAYMENT_INVALID)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturn200OnValidPut() throws Exception {
    whenRequestedbyIdThenReturnAsset();

    mockMvc.perform(put("/api/payment/261465a7-368c-4c9b-bbad-7e9a06bd3b00")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(MOCK_CREATION_PAYMENT_VALID)))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldReturn400OnInvalidPostId() throws Exception {
    mockMvc.perform(put("/api/payment/invalidId")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(MOCK_CREATION_PAYMENT_INVALID)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void shouldReturn200OnGetAll() throws Exception {
    mockMvc.perform(get("/api/payment"))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldReturn404OnGetNotExistingPayment() throws Exception {
    whenRequestedbyIdThenAssetShuldNotBeFound();

    mockMvc.perform(get("/api/payment/261465a7-368c-4c9b-bbad-7e9a06bd3b00"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void shouldReturnPaymentById() throws Exception {
    whenRequestedbyIdThenReturnAsset();

    mockMvc.perform(get("/api/payment/261465a7-368c-4c9b-bbad-7e9a06bd3b00"))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldReturn400OnNotExistingDelete() throws Exception {
    whenRequestedbyIdThenAssetShuldNotBeFound();

    mockMvc.perform(delete("/api/payment/261465a7-368c-4c9b-bbad-7e9a06bd3b00"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void shouldReturn200OnValidDelete() throws Exception {
    whenRequestedbyIdThenReturnAsset();

    mockMvc.perform(delete("/api/payment/261465a7-368c-4c9b-bbad-7e9a06bd3b00"))
        .andExpect(status().isOk());
  }

  private void whenRequestedbyIdThenReturnAsset() {
    when(paymentService.getPaymentById(any())).thenReturn(Optional.of(Payment.builder().build()));
  }

  private void whenRequestedbyIdThenAssetShuldNotBeFound() {
    when(paymentService.getPaymentById(any())).thenReturn(Optional.empty());
  }
}
