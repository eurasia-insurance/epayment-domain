package com.lapsa.kkb.core;

public class KKBPaymentErrorDocument extends KKBDocument {
    private static final long serialVersionUID = -3421596583719107336L;
    private static final int PRIME = 19;
    private static final int MULTIPLIER = 19;

    @Override
    protected int getPrime() {
	return PRIME;
    }

    @Override
    protected int getMultiplier() {
	return MULTIPLIER;
    }
}
