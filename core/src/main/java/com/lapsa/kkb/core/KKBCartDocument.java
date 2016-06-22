package com.lapsa.kkb.core;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class KKBCartDocument extends KKBDocument {
    private static final long serialVersionUID = 3602329001760876393L;
    private static final int PRIME = 3;
    private static final int MULTIPLIER = 3;

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
	KKBCartDocument that = (KKBCartDocument) other;
	return new EqualsBuilder()
		.appendSuper(super.equals(that))
		.isEquals();
    }

    // GENERATED

    public KKBCartDocument() {
	super();
    }

    public KKBCartDocument(String content) {
	super(content);
    }
}
