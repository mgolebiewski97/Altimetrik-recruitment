package com.mgolebiewski.recruitment.application.rest;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {
  private String errorCode;
  private List<String> errors;
}
