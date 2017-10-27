package com.lapsa.kkb.core;

import java.time.Instant;
import java.util.Base64;

public abstract class KKBDocument extends KKBBaseEntity<Integer> {
    private static final long serialVersionUID = -9155395037288903019L;

    protected String content;

    protected Instant created;

    protected KKBOrder order;

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

    public Instant getCreated() {
	return created;
    }

    public void setCreated(Instant created) {
	this.created = created;
    }

    public KKBOrder getOrder() {
	return order;
    }

    protected void setOrder(KKBOrder order) {
	this.order = order;
    }
}