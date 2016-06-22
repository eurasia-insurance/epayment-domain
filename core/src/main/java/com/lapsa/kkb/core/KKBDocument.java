package com.lapsa.kkb.core;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class KKBDocument extends BaseEntity<Integer> {
    private static final long serialVersionUID = -9155395037288903019L;

    private String content;

    private Date created;

    private KKBOrder order;

    @Override
    public int hashCode() {
	return new HashCodeBuilder(getPrime(), getMultiplier())
		.appendSuper(super.hashCode())
		.append(content)
		.append(created)
		.append(order)
		.toHashCode();
    }

    @Override
    public boolean equals(Object other) {
	if (other == null || other.getClass() != getClass())
	    return false;
	if (other == this)
	    return true;
	KKBDocument that = (KKBDocument) other;
	return new EqualsBuilder()
		.appendSuper(super.equals(that))
		.append(content, that.content)
		.append(created, that.created)
		.append(order, that.order)
		.isEquals();
    }

    // GENERATED

    public KKBDocument() {
    }

    public KKBDocument(String content) {
	this.content = content;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public Date getCreated() {
	return created;
    }

    public void setCreated(Date created) {
	this.created = created;
    }

    public KKBOrder getOrder() {
	return order;
    }

    @Deprecated
    public void setOrder(KKBOrder order) {
	this.order = order;
    }
}