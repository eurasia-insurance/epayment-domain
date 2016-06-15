package com.lapsa.kkb.api;

public class KKBServiceError extends Exception {
    private static final long serialVersionUID = 4268932787626581241L;

    public KKBServiceError() {
	super();
    }

    public KKBServiceError(String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public KKBServiceError(String message, Throwable cause) {
	super(message, cause);
    }

    public KKBServiceError(String message) {
	super(message);
    }

    public KKBServiceError(Throwable cause) {
	super(cause);
    }

}
