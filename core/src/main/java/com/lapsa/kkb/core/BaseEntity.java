package com.lapsa.kkb.core;

import java.io.Serializable;

public abstract class BaseEntity<T> extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 2914122165051543297L;

    protected T id;

    @Override
    public String toString() {
	return String.format("%1$s.ID:%2$s", this.getClass().getSimpleName(), id != null ? id : "NULL");
    }

    // GENERATED

    public T getId() {
	return id;
    }

    public void setId(T id) {
	this.id = id;
    }

}
