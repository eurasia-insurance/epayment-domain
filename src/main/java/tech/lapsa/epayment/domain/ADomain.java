package tech.lapsa.epayment.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import tech.lapsa.java.commons.localization.Localized;

public abstract class ADomain implements Serializable, Localized {

    private static final long serialVersionUID = 1L;

    private final int PRIME;
    private final int MULTIPLIER;

    protected ADomain(int prime, int multiplier) {
	this.PRIME = prime;
	this.MULTIPLIER = multiplier;
    }

    protected ADomain(int prime) {
	this(prime, prime);
    }

    @Override
    public String toString() {
	return regular();
    }

    @Override
    public final int hashCode() {
	return HashCodeBuilder.reflectionHashCode(PRIME, MULTIPLIER, this, false);
    }

    @Override
    public final boolean equals(Object other) {
	return EqualsBuilder.reflectionEquals(this, other, false);
    }

}
