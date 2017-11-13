package tech.lapsa.epayment.domain;

public class NumberOfAttemptsExceedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NumberOfAttemptsExceedException() {
	super();
    }

    public NumberOfAttemptsExceedException(String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public NumberOfAttemptsExceedException(String message, Throwable cause) {
	super(message, cause);
    }

    public NumberOfAttemptsExceedException(String message) {
	super(message);
    }

    public NumberOfAttemptsExceedException(Throwable cause) {
	super(cause);
    }
}
