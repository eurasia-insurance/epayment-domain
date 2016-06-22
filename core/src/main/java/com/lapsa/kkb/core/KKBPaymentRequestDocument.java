package com.lapsa.kkb.core;

public class KKBPaymentRequestDocument extends KKBDocument {
    private static final long serialVersionUID = -4716980468384620898L;
    private static final int PRIME = 11;
    private static final int MULTIPLIER = 11;

    @Override
    protected int getPrime() {
	return PRIME;
    }

    @Override
    protected int getMultiplier() {
	return MULTIPLIER;
    }

    // GENERATED

    public KKBPaymentRequestDocument() {
	super();
    }

    public KKBPaymentRequestDocument(String content) {
	super(content);
    }
}