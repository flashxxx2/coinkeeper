package payments.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import payments.exception.PaymentNotFoundException;

import java.util.Map;

@RestControllerAdvice
public class AppRestControllerAdvice {
  @ExceptionHandler // TODO: replace map with DTO
  public ResponseEntity<Map<String, Object>> handleApplicationException(PaymentNotFoundException e) {
    // TODO:
    //  1. Status code
    //  2. Application error code -> "err.not_enough_balance"
    // TODO: get status from exception
    return ResponseEntity.status(400).body(Map.of("code", "err..."));
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, Object> handleException(Exception e) {
    return Map.of("code", "err.unknown");
  }
}
