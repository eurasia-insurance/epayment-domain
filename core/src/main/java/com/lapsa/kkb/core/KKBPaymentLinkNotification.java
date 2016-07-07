package com.lapsa.kkb.core;

public class KKBPaymentLinkNotification extends KKBOrderNotification {
    private static final long serialVersionUID = -2254389162393105750L;
    private static final int PRIME = 29;
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
