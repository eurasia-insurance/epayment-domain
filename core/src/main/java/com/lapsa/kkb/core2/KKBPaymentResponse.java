package com.lapsa.kkb.core2;

import java.util.Date;

public class KKBPaymentResponse extends BaseEntity<Integer> {
    private static final long serialVersionUID = 3038748813011463215L;

    private KKBOrder order;

    private String xmlContent;

    private Date received;

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

    public void setXmlContent(String xmlContent) {
	this.xmlContent = xmlContent;
    }

    public Date getReceived() {
	return received;
    }

    public void setReceived(Date received) {
	this.received = received;
    }
}
