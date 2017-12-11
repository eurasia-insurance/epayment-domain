package tech.lapsa.epayment.domain;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import tech.lapsa.java.commons.localization.Localized;
import tech.lapsa.patterns.domain.MyHcEqToStr;

@MappedSuperclass
public abstract class Domain implements Localized, Serializable {

    private static final long serialVersionUID = 1L;

    public Domain() {
    }

    @Override
    public String toString() {
	return localized(LocalizationVariant.NORMAL, MyHcEqToStr.toStringLocaleOf(this.getClass()));
    }

    @Override
    public final int hashCode() {
	return MyHcEqToStr.hashCode(this);
    }

    @Override
    public final boolean equals(final Object other) {
	return MyHcEqToStr.equals(this, other);
    }

    public void unlazy() {
    }
}
