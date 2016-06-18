package com.lapsa.kkb.core2;

import java.util.Date;

public class KKBPaymentReuest extends BaseEntity<Integer> {
    private static final long serialVersionUID = -9155395037288903019L;

    private KKBOrder order;

    private String xmlContent;

    private Date sent;

    // GENERATED

    public KKBOrder getOrder() {
	return order;
    }

    public void setOrder(KKBOrder order) {
	this.order = order;
    }

    public String getXmlContent() {
	return xmlContent;
    }

    public void setXmlContent(String xml) {
	this.xmlContent = xml;
    }

    public Date getSent() {
	return sent;
    }

    public void setSent(Date sent) {
	this.sent = sent;
    }
}