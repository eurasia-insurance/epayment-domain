package com.lapsa.kkb.api;

public class KKBEncodingException extends Exception {
    private static final long serialVersionUID = 4669852557357298525L;

    public KKBEncodingException() {
	super();
    }

    public KKBEncodingException(String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public KKBEncodingException(String message, Throwable cause) {
	super(message, cause);
    }

    public KKBEncodingException(String message) {
	super(message);
    }

    public KKBEncodingException(Throwable cause) {
	super(cause);
    }

}
