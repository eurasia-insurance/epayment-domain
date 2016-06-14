package com.lapsa.kkb.api;

public class KKBResponseFormatValidationError extends Exception {
    private static final long serialVersionUID = 4268932787626581241L;

    public KKBResponseFormatValidationError() {
	super();
    }

    public KKBResponseFormatValidationError(String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public KKBResponseFormatValidationError(String message, Throwable cause) {
	super(message, cause);
    }

    public KKBResponseFormatValidationError(String message) {
	super(message);
    }

    public KKBResponseFormatValidationError(Throwable cause) {
	super(cause);
    }

}
