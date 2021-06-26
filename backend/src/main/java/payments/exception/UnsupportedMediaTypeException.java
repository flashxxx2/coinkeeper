package payments.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class UnsupportedMediaTypeException extends RuntimeException {

    @Getter
    private final HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

    @Getter
    private final String message = "Отправленный вами формат не поддерживается";

    public UnsupportedMediaTypeException() {
        super();
    }

    public UnsupportedMediaTypeException(String message) {
        super(message);
    }

    public UnsupportedMediaTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedMediaTypeException(Throwable cause) {
        super(cause);
    }

    protected UnsupportedMediaTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
