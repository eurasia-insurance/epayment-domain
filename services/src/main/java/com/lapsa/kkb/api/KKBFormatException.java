package com.lapsa.kkb.api;

public class KKBFormatException extends Exception {
    private static final long serialVersionUID = -1918991731019820834L;

    public KKBFormatException() {
	super();
    }

    public KKBFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public KKBFormatException(String message, Throwable cause) {
	super(message, cause);
    }

    public KKBFormatException(String message) {
	super(message);
    }

    public KKBFormatException(Throwable cause) {
	super(cause);
    }

}
