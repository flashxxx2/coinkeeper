package payments.exception;

public class IllegalCoastPurchaseException extends RuntimeException {
    public IllegalCoastPurchaseException() {
        super();
    }

    public IllegalCoastPurchaseException(String message) {
        super(message);
    }

    public IllegalCoastPurchaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCoastPurchaseException(Throwable cause) {
        super(cause);
    }

    protected IllegalCoastPurchaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
