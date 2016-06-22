package com.lapsa.kkb.core;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public int hashCode() {
	return new HashCodeBuilder(getPrime(), getMultiplier())
		.appendSuper(super.hashCode())
		.toHashCode();
    }

    @Override
    public boolean equals(Object other) {
	if (other == null || other.getClass() != getClass())
	    return false;
	if (other == this)
	    return true;
	KKBOrderItem that = (KKBOrderItem) other;
	return new EqualsBuilder()
		.appendSuper(super.equals(that))
		.isEquals();
    }

    // GENERATED

    public KKBPaymentRequestDocument() {
	super();
    }

    public KKBPaymentRequestDocument(String content) {
	super(content);
    }
}