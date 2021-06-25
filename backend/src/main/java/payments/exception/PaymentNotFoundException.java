package payments.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class PaymentNotFoundException extends RuntimeException {

    @Getter
    private final HttpStatus status = HttpStatus.NOT_FOUND;

    @Getter
    private final String message = "Расход не найден";

    public PaymentNotFoundException() {
        super();
    }

    public PaymentNotFoundException(String message) {
        super(message);
    }

    public PaymentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentNotFoundException(Throwable cause) {
        super(cause);
    }

    protected PaymentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
