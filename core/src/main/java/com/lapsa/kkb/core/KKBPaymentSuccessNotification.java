package com.lapsa.kkb.core;

public class KKBPaymentSuccessNotification extends KKBOrderNotification {
    private static final long serialVersionUID = -7150273889063716969L;
    private static final int PRIME = 23;
    private static final int MULTIPLIER = PRIME;

    @Override
    protected int getPrime() {
	return PRIME;
    }

    @Override
    protected int getMultiplier() {
	return MULTIPLIER;
    }
}
