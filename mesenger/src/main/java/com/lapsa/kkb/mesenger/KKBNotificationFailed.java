package com.lapsa.kkb.mesenger;

public class KKBNotificationFailed extends Exception {

    private static final long serialVersionUID = -5063540628264203040L;

    public KKBNotificationFailed() {
	super();
    }

    public KKBNotificationFailed(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public KKBNotificationFailed(String message, Throwable cause) {
	super(message, cause);
    }

    public KKBNotificationFailed(String message) {
	super(message);
    }

    public KKBNotificationFailed(Throwable cause) {
	super(cause);
    }
}
