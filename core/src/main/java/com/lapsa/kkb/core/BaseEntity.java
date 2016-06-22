package com.lapsa.kkb.core;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class BaseEntity<T> extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 2914122165051543297L;

    protected T id;

    @Override
    public int hashCode() {
	return new HashCodeBuilder(getPrime(), getMultiplier())
		.append(id)
		.toHashCode();
    }

    @Override
    public boolean equals(Object other) {
	if (other == null || other.getClass() != getClass())
	    return false;
	if (other == this)
	    return true;
	@SuppressWarnings("unchecked")
	BaseEntity<T> that = (BaseEntity<T>) other;
	return new EqualsBuilder()
		.append(id, that.id)
		.isEquals();
    }

    // GENERATED

    public T getId() {
	return id;
    }

    public void setId(T id) {
	this.id = id;
    }

}
