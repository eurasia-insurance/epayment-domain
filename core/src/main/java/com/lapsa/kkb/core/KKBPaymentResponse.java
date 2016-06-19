package com.lapsa.kkb.core;

import java.util.Date;

public class KKBPaymentResponse extends BaseEntity<Integer> {
    private static final long serialVersionUID = 3038748813011463215L;

    private KKBOrder order;

    private String content;

    private Date received;

    // GENERATED

    public KKBOrder getOrder() {
	return order;
    }

    public void setOrder(KKBOrder order) {
	this.order = order;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public Date getReceived() {
	return received;
    }

    public void setReceived(Date received) {
	this.received = received;
    }
}
