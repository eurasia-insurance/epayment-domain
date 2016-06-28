package com.lapsa.kkb.core;

import java.util.Base64;
import java.util.Date;

public abstract class KKBDocument extends KKBBaseEntity<Integer> {
    private static final long serialVersionUID = -9155395037288903019L;

    private String content;

    private Date created;

    private KKBOrder order;

    public String getContentBase64() {
	if (content == null)
	    return null;
	return Base64.getEncoder().encodeToString(content.getBytes());
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