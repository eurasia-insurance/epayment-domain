package com.lapsa.kkb.core;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class BaseEntity<T> extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 2914122165051543297L;

    protected T id;

    @Override
    public String toString() {
	return String.format("%1$s.ID:%2$s", this.getClass().getSimpleName(), id != null ? id : "NULL");
    }

    @Override
    public int hashCode() {
	HashCodeBuilder hcb = new HashCodeBuilder();
	if (id == null)
	    return hcb.appendSuper(super.hashCode()).toHashCode();
	return hcb.append(id).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
	if (other == null || other.getClass() != getClass())
	    return false;
	if (other == this)
	    return true;

	@SuppressWarnings("unchecked")
	BaseEntity<T> that = (BaseEntity<T>) other;
	EqualsBuilder eb = new EqualsBuilder();
	if (id == null)
	    return eb.appendSuper(super.equals(that)).isEquals();
	return eb.append(id, that.id).isEquals();
    }

    // GENERATED

    public T getId() {
	return id;
    }

    @Deprecated
    public void setId(T id) {
	this.id = id;
    }

}
