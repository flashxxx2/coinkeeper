package payments.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class PaymentImagesNotFoundException extends RuntimeException {

    @Getter
    private final HttpStatus status = HttpStatus.NOT_FOUND;

    @Getter
    private final String message = "Изображения не найдены";

    public PaymentImagesNotFoundException() {
        super();
    }

    public PaymentImagesNotFoundException(String message) {
        super(message);
    }

    public PaymentImagesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentImagesNotFoundException(Throwable cause) {
        super(cause);
    }

    protected PaymentImagesNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
