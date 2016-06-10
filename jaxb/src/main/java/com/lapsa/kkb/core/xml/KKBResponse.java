package com.lapsa.kkb.core.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class KKBResponse implements Serializable {
    private static final long serialVersionUID = -4672326400778882641L;

    // order_id - номер заказа
    @XmlAttribute(name = "order_id", required = true)
    private String orderId;

    @XmlElementRef(required = true)
    private KKBError error;

    @XmlElementRef(required = true)
    private KKBSession session;

    // GENERATED

    public String getOrderId() {
	return orderId;
    }

    public void setOrderId(String orderId) {
	this.orderId = orderId;
    }

    public KKBError getError() {
	return error;
    }

    public void setError(KKBError error) {
	this.error = error;
    }

    public KKBSession getSession() {
	return session;
    }

    public void setSession(KKBSession session) {
	this.session = session;
    }

}
