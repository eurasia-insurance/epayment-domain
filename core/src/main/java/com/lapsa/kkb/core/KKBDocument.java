package com.lapsa.kkb.core;

import java.util.Date;

public abstract class KKBDocument extends BaseEntity<Integer> {
    private static final long serialVersionUID = -9155395037288903019L;

    private String content;

    private Date created;

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
}