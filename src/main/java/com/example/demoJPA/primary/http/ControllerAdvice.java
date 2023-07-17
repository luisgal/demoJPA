package com.example.demoJPA.primary.http;

import com.example.demoJPA.core.exception.ResourceNotFound;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {

  private final MessageSource messageSource;

  record Violation(String path, String value) {

  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ProblemDetail onConstraintViolationException(
      ConstraintViolationException constraintViolationException) {

    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problemDetail.setTitle(HttpStatus.BAD_REQUEST.getReasonPhrase());
    problemDetail.setDetail("ERROR validating path");

    List<Violation> location = new ArrayList<>();

    for (ConstraintViolation violation : constraintViolationException.getConstraintViolations()) {
      location.add(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
    }

    problemDetail.setProperty("location", location);

    return problemDetail;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail onMethodArgumentNotValidException(
      MethodArgumentNotValidException methodArgumentNotValidException) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problemDetail.setTitle(HttpStatus.BAD_REQUEST.getReasonPhrase());
    problemDetail.setDetail("Error validating request body");

    List<Violation> location = methodArgumentNotValidException
        .getBindingResult()
        .getFieldErrors()
        .stream()
        .map(fieldError -> new Violation(fieldError.getField(), fieldError.getDefaultMessage()))
        .collect(Collectors.toList());

    problemDetail.setProperty("location", location);

    return problemDetail;
  }

  @ExceptionHandler(ResourceNotFound.class)
  public ProblemDetail onResourceNotFound(ResourceNotFound resourceNotFound) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
    problemDetail.setTitle(HttpStatus.NOT_FOUND.getReasonPhrase());
    problemDetail.setDetail(getLocalizedMessage(
        "resource.not.found.message",
        resourceNotFound.getMessage(),
        resourceNotFound.getId())
    );

    return problemDetail;
  }

  String getLocalizedMessage(String code, Object... args) {
    Locale locale = LocaleContextHolder.getLocale();
    return messageSource.getMessage(code, args, locale);
  }
}
