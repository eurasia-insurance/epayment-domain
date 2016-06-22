package com.lapsa.kkb.core;

import java.io.Serializable;
import java.util.UUID;

public abstract class BaseDomain implements Serializable {
    private static final long serialVersionUID = 3664529817399340371L;

    protected transient final UUID instanceUUID = UUID.randomUUID();
    protected transient final String instanceWebSafeUUID = "UUID" + instanceUUID.toString().replace("-", "");

    protected abstract int getPrime();

    protected abstract int getMultiplier();

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object other);

    public final UUID getInstanceUUID() {
	return instanceUUID;
    }

    public final String getInstanceWebSafeUUID() {
	return instanceWebSafeUUID;
    }

}
