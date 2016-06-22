package com.lapsa.kkb.persistence;

public class KKBEntityNotFound extends Exception {
    private static final long serialVersionUID = -2183850752424900639L;

    public KKBEntityNotFound() {
	super();
    }

    public KKBEntityNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public KKBEntityNotFound(String message, Throwable cause) {
	super(message, cause);
    }

    public KKBEntityNotFound(String message) {
	super(message);
    }

    public KKBEntityNotFound(Throwable cause) {
	super(cause);
    }

}
