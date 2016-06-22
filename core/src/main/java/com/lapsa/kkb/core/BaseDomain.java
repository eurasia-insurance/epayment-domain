package com.lapsa.kkb.core;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class BaseDomain implements Serializable {
    private static final long serialVersionUID = 3664529817399340371L;

    protected transient final UUID instanceUUID = UUID.randomUUID();
    protected transient final String instanceWebSafeUUID = "UUID" + instanceUUID.toString().replace("-", "");

    protected abstract int getPrime();

    protected abstract int getMultiplier();

    @Override
    public int hashCode() {
	return new HashCodeBuilder(getPrime(), getMultiplier())
		.append(instanceUUID)
		.toHashCode();

    }

    @Override
    public boolean equals(Object other) {
	if (other == null || other.getClass() != getClass())
	    return false;
	if (other == this)
	    return true;
	BaseDomain that = (BaseDomain) other;
	return new EqualsBuilder()
		.append(instanceUUID, that.instanceUUID)
		.isEquals();

    }

    public final UUID getInstanceUUID() {
	return instanceUUID;
    }

    public final String getInstanceWebSafeUUID() {
	return instanceWebSafeUUID;
    }

}
