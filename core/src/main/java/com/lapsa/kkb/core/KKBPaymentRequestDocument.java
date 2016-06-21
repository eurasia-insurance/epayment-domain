package com.lapsa.kkb.core;

public class KKBPaymentRequestDocument extends KKBDocument {
    private static final long serialVersionUID = -4716980468384620898L;

    private KKBOrder order;

    // GENERATED

    public KKBPaymentRequestDocument() {
	super();
    }

    public KKBPaymentRequestDocument(String content) {
	super(content);
    }

    public KKBOrder getOrder() {
	return order;
    }

    public void setOrder(KKBOrder order) {
	this.order = order;
    }
}