package com.lapsa.kkb.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "customer_sign")
public class KKBCustomerSign implements Serializable {
    private static final long serialVersionUID = 6758256294210679603L;

    // type - Тип подписи
    @XmlAttribute(name = "type")
    private KKBSignType type;

    // подпись
    @XmlValue
    private byte[] signature;

    // GENERATED

    public KKBSignType getType() {
	return type;
    }

    public void setType(KKBSignType type) {
	this.type = type;
    }

    public byte[] getSignature() {
	return signature;
    }

    public void setSignature(byte[] signature) {
	this.signature = signature;
    }

}
