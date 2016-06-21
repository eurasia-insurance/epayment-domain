package com.lapsa.kkb.services;

public class KKBValidationErrorException extends Exception {
    private static final long serialVersionUID = -8610626115834667633L;

    public KKBValidationErrorException() {
	super();
    }

    public KKBValidationErrorException(String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public KKBValidationErrorException(String message, Throwable cause) {
	super(message, cause);
    }

    public KKBValidationErrorException(String message) {
	super(message);
    }

    public KKBValidationErrorException(Throwable cause) {
	super(cause);
    }

}
