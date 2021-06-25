package payments.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import payments.exception.PaymentImagesNotFoundException;

import java.util.Map;

@RestControllerAdvice
public class AppRestControllerAdvice {
  @ExceptionHandler
  public ResponseEntity<Map<String, Object>> handleApplicationException(PaymentImagesNotFoundException e) {
    return ResponseEntity.status(400).body(Map.of("code", "Something wrong"));
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public Map<String, Object> handleException(Exception e) {
    return Map.of("code", "Do you wanna hack me?");
  }
}
