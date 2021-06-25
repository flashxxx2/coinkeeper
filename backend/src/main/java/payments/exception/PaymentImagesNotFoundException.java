package payments.exception;

public class PaymentImagesNotFoundException extends RuntimeException {
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
