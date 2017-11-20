package tech.lapsa.epayment.domain;

public final class Exceptions {

    private Exceptions() {
    }

    public static class IsNotPaidException extends IllegalStateException {

	private static final long serialVersionUID = 1L;

	public IsNotPaidException() {
	    super();
	}

	public IsNotPaidException(String message, Throwable cause) {
	    super(message, cause);
	}

	public IsNotPaidException(String s) {
	    super(s);
	}

	public IsNotPaidException(Throwable cause) {
	    super(cause);
	}
    }

    public static class IsNotPendingException extends IllegalStateException {

	private static final long serialVersionUID = 1L;

	public IsNotPendingException() {
	    super();
	}

	public IsNotPendingException(String message, Throwable cause) {
	    super(message, cause);
	}

	public IsNotPendingException(String s) {
	    super(s);
	}

	public IsNotPendingException(Throwable cause) {
	    super(cause);
	}
    }

    public static class IsPaidException extends IllegalStateException {

	private static final long serialVersionUID = 1L;

	public IsPaidException() {
	    super();
	}

	public IsPaidException(String message, Throwable cause) {
	    super(message, cause);
	}

	public IsPaidException(String s) {
	    super(s);
	}

	public IsPaidException(Throwable cause) {
	    super(cause);
	}
    }
}
