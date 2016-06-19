package com.lapsa.kkb.core;

import java.io.Serializable;

public abstract class BaseEntity<T> extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 2914122165051543297L;

    protected T id;

    @Override
    public int hashCode() {
	return this.getClass().hashCode()
		* (id != null ? id.hashCode() : super.instanceUUID.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
	return obj != null
		&& this.getClass().isInstance(obj)
		&& ((id == null && instanceUUID.equals(this.getClass().cast(obj).instanceUUID))
			|| (id != null && getId().equals((this.getClass().cast(obj)).id)));
    }

    // GENERATED

    public T getId() {
	return id;
    }

    public void setId(T id) {
	this.id = id;
    }

}
