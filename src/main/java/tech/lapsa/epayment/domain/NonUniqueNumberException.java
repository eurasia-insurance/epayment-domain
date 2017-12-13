package tech.lapsa.epayment.domain;

public class NonUniqueNumberException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NonUniqueNumberException() {
	super();
    }

    public NonUniqueNumberException(final String message, final Throwable cause) {
	super(message, cause);
    }

    public NonUniqueNumberException(final String message) {
	super(message);
    }

    public NonUniqueNumberException(final Throwable cause) {
	super(cause);
    }
}
