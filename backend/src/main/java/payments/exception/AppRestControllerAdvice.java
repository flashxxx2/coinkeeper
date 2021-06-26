package payments.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import payments.models.ExceptionModel;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class AppRestControllerAdvice {

  @ExceptionHandler
  public ResponseEntity<ExceptionModel> handleApplicationException(PaymentNotFoundException exception,
                                                                   HandlerMethod handlerMethod) {
    String message = exception.getMessage();
    log.error(message, exception);
    return new ResponseEntity<>(ExceptionModel.builder()
            .message(message)
            .exceptionName(exception.getClass().getName())
            .build(), exception.getStatus());
  }

  @ExceptionHandler
  public ResponseEntity<ExceptionModel> handleApplicationException(PaymentImagesNotFoundException exception,
                                                                   HandlerMethod handlerMethod) {
    String message = exception.getMessage();
    log.error(message, exception);
    return new ResponseEntity<>(ExceptionModel.builder()
            .message(message)
            .exceptionName(exception.getClass().getName())
            .build(), exception.getStatus());
  }

  @ExceptionHandler
  public ResponseEntity<ExceptionModel> handleApplicationException(UnsupportedMediaTypeException exception,
                                                                   HandlerMethod handlerMethod) {
    String message = exception.getMessage();
    log.error(message, exception);
    return new ResponseEntity<>(ExceptionModel.builder()
            .message(message)
            .exceptionName(exception.getClass().getName())
            .build(), exception.getStatus());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, Object> handleException(Exception e) {
    return Map.of("code", "Do you wanna hack me?");
  }
}
