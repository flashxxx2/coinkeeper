package payments.exception;

public class PaymentNotFoundExeption extends RuntimeException {
    public PaymentNotFoundExeption() {
        super();
    }

    public PaymentNotFoundExeption(String message) {
        super(message);
    }

    public PaymentNotFoundExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentNotFoundExeption(Throwable cause) {
        super(cause);
    }

    protected PaymentNotFoundExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
