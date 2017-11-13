package tech.lapsa.epayment.domain;

public class NonUniqueNumberException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NonUniqueNumberException() {
	super();
    }

    public NonUniqueNumberException(String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public NonUniqueNumberException(String message, Throwable cause) {
	super(message, cause);
    }

    public NonUniqueNumberException(String message) {
	super(message);
    }

    public NonUniqueNumberException(Throwable cause) {
	super(cause);
    }
}
