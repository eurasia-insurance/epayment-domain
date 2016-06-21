package com.lapsa.kkb.core;

public class KKBCartDocument extends KKBDocument {
    private static final long serialVersionUID = 3602329001760876393L;

    private KKBOrder order;

    // GENERATED

    public KKBCartDocument() {
	super();
    }

    public KKBCartDocument(String content) {
	super(content);
    }

    public KKBOrder getOrder() {
	return order;
    }

    public void setOrder(KKBOrder order) {
	this.order = order;
    }
}
