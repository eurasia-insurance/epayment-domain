package tech.lapsa.epayment.domain;

public class NumberOfAttemptsExceedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NumberOfAttemptsExceedException() {
	super();
    }

    public NumberOfAttemptsExceedException(final String message, final Throwable cause) {
	super(message, cause);
    }

    public NumberOfAttemptsExceedException(final String message) {
	super(message);
    }

    public NumberOfAttemptsExceedException(final Throwable cause) {
	super(cause);
    }
}
