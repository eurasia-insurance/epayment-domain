package com.lapsa.kkb.api;

public class KKBWrongSignature extends Exception {
    private static final long serialVersionUID = -4314988417703788061L;

    public KKBWrongSignature() {
	super();
    }

    public KKBWrongSignature(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public KKBWrongSignature(String message, Throwable cause) {
	super(message, cause);
    }

    public KKBWrongSignature(String message) {
	super(message);
    }

    public KKBWrongSignature(Throwable cause) {
	super(cause);
    }
}
