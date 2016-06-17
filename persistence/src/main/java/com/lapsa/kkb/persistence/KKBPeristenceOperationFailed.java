package com.lapsa.kkb.persistence;

public class KKBPeristenceOperationFailed extends Exception {

    private static final long serialVersionUID = 161779605475547163L;

    public KKBPeristenceOperationFailed() {
	super();
    }

    public KKBPeristenceOperationFailed(String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public KKBPeristenceOperationFailed(String message, Throwable cause) {
	super(message, cause);
    }

    public KKBPeristenceOperationFailed(String message) {
	super(message);
    }

    public KKBPeristenceOperationFailed(Throwable cause) {
	super(cause);
    }
}
