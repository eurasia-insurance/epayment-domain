package tech.lapsa.epayment.domain;

public final class Exceptions {

    private Exceptions() {
    }

    public static class IsNotPaidException extends IllegalStateException {

	private static final long serialVersionUID = 1L;

	public IsNotPaidException() {
	    super();
	}

	public IsNotPaidException(final String message, final Throwable cause) {
	    super(message, cause);
	}

	public IsNotPaidException(final String s) {
	    super(s);
	}

	public IsNotPaidException(final Throwable cause) {
	    super(cause);
	}
    }

    public static class IsNotPendingException extends IllegalStateException {

	private static final long serialVersionUID = 1L;

	public IsNotPendingException() {
	    super();
	}

	public IsNotPendingException(final String message, final Throwable cause) {
	    super(message, cause);
	}

	public IsNotPendingException(final String s) {
	    super(s);
	}

	public IsNotPendingException(final Throwable cause) {
	    super(cause);
	}
    }

    public static class IsPaidException extends IllegalStateException {

	private static final long serialVersionUID = 1L;

	public IsPaidException() {
	    super();
	}

	public IsPaidException(final String message, final Throwable cause) {
	    super(message, cause);
	}

	public IsPaidException(final String s) {
	    super(s);
	}

	public IsPaidException(final Throwable cause) {
	    super(cause);
	}
    }

    public static class IsNotExpiredException extends IllegalStateException {

	private static final long serialVersionUID = 1L;

	public IsNotExpiredException() {
	    super();
	}

	public IsNotExpiredException(final String message, final Throwable cause) {
	    super(message, cause);
	}

	public IsNotExpiredException(final String s) {
	    super(s);
	}

	public IsNotExpiredException(final Throwable cause) {
	    super(cause);
	}
    }
}
