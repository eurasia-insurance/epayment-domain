package com.lapsa.kkb.core;

public class KKBPaymentResponseDocument extends KKBDocument {
    private static final long serialVersionUID = -3421596583719107336L;

    private KKBOrder order;

    // GENERATED

    public KKBPaymentResponseDocument() {
	super();
    }

    public KKBPaymentResponseDocument(String content) {
	super(content);
    }

    public KKBOrder getOrder() {
	return order;
    }

    public void setOrder(KKBOrder order) {
	this.order = order;
    }
}
