package tech.lapsa.epayment.domain;

import java.io.Serializable;
import java.util.Locale;

import javax.persistence.MappedSuperclass;

import tech.lapsa.java.commons.localization.Localized;
import tech.lapsa.patterns.domain.MyHcEqToStr;

@MappedSuperclass
public abstract class Domain implements Localized, Serializable {

    private static final long serialVersionUID = 1L;

    private final Integer prime;
    private final Integer multiplier;
    private final Locale locale;

    public Domain() {
	prime = MyHcEqToStr.primeOf(this.getClass());
	multiplier = MyHcEqToStr.multiplierOf(this.getClass(), prime);
	locale = MyHcEqToStr.toStringLocaleOf(this.getClass());
    }

    @Override
    public String toString() {
	return localized(LocalizationVariant.NORMAL, locale);
    }

    @Override
    public final int hashCode() {
	return MyHcEqToStr.hashCode(this, prime, multiplier);
    }

    @Override
    public final boolean equals(final Object other) {
	return MyHcEqToStr.equals(this, other);
    }

    public void unlazy() {
    }
}
