package com.mgolebiewski.recruitment.application.rest;

import java.util.Collections;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ControllerExceptionHandler {

  public static final String BAD_REQUEST_CODE = "400 Bad request";

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorMessage> exceptionHandler(ValidationException e) {
    return new ResponseEntity<>(new ErrorMessage(
        BAD_REQUEST_CODE,
        Collections.singletonList(e.getMessage())),
        HttpStatus.BAD_REQUEST
    );
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorMessage> exceptionHandler(ConstraintViolationException e) {
    return new ResponseEntity<>(new ErrorMessage(
        BAD_REQUEST_CODE,
        e.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList())),
        HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorMessage> exceptionHandler(ResponseStatusException e) {
    return new ResponseEntity<>(new ErrorMessage(
        String.valueOf(e.getRawStatusCode()),
        Collections.singletonList(e.getReason())),
        HttpStatus.valueOf(e.getRawStatusCode()));
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorMessage> exceptionHandler(MethodArgumentNotValidException e) {
    return new ResponseEntity<>(new ErrorMessage(
        BAD_REQUEST_CODE,
        Collections.singletonList(e.getMessage())),
        HttpStatus.BAD_REQUEST
    );
  }
}
