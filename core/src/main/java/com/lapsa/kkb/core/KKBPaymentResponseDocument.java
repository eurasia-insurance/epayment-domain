package com.lapsa.kkb.core;

public class KKBPaymentResponseDocument extends KKBDocument {
    private static final long serialVersionUID = -3421596583719107336L;
    private static final int PRIME = 13;
    private static final int MULTIPLIER = 13;

    @Override
    protected int getPrime() {
	return PRIME;
    }

    @Override
    protected int getMultiplier() {
	return MULTIPLIER;
    }

    // GENERATED

    public KKBPaymentResponseDocument() {
	super();
    }

    public KKBPaymentResponseDocument(String content) {
	super(content);
    }
}
