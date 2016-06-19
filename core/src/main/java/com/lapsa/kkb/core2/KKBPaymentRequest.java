package com.lapsa.kkb.core2;

import java.util.Date;

public class KKBPaymentRequest extends BaseEntity<Integer> {
    private static final long serialVersionUID = -9155395037288903019L;

    private KKBOrder order;

    private String content;

    private Date sent;

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

    public Date getSent() {
	return sent;
    }

    public void setSent(Date sent) {
	this.sent = sent;
    }
}