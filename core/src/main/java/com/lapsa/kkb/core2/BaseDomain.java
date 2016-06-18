package com.lapsa.kkb.core2;

import java.io.Serializable;
import java.util.UUID;

public abstract class BaseDomain implements Serializable {
    private static final long serialVersionUID = 3664529817399340371L;

    protected transient final UUID instanceUUID = UUID.randomUUID();
    protected transient final String instanceWebSafeUUID = "UUID" + instanceUUID.toString().replace("-", "");

    @Override
    public int hashCode() {
	return this.getClass().hashCode()
		* instanceUUID.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	return obj != null
		&& this.getClass().isInstance(obj)
		&& (instanceUUID.equals(this.getClass().cast(obj).instanceUUID));
    }

    public final UUID getInstanceUUID() {
	return instanceUUID;
    }

    public final String getInstanceWebSafeUUID() {
	return instanceWebSafeUUID;
    }

}
