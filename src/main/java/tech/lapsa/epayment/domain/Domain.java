package tech.lapsa.epayment.domain;

import java.io.Serializable;
import java.util.Locale;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import tech.lapsa.java.commons.localization.Localized;

public abstract class Domain implements Serializable, Localized {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
	return regular(Locale.ENGLISH);
    }

    protected abstract int prime();

    public void unlazy() {
    }

    @Override
    public final int hashCode() {
	return HashCodeBuilder.reflectionHashCode(prime(), prime(), this, false);
    }

    @Override
    public final boolean equals(final Object other) {
	return EqualsBuilder.reflectionEquals(this, other, false);
    }

}
