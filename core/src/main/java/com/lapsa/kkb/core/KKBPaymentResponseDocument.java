package com.lapsa.kkb.core;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    public KKBPaymentResponseDocument() {
	super();
    }

    public KKBPaymentResponseDocument(String content) {
	super(content);
    }
}
