package com.lapsa.kkb.services;

public class KKBSignatureOperationFailed extends KKBServiceError {
    private static final long serialVersionUID = 2385227097319192621L;

    public KKBSignatureOperationFailed() {
	super();
    }

    public KKBSignatureOperationFailed(String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public KKBSignatureOperationFailed(String message, Throwable cause) {
	super(message, cause);
    }

    public KKBSignatureOperationFailed(String message) {
	super(message);
    }

    public KKBSignatureOperationFailed(Throwable cause) {
	super(cause);
    }

}
