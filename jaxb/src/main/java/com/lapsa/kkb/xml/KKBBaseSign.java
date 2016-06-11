package com.lapsa.kkb.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public abstract class KKBBaseSign implements Serializable {

    private static final long serialVersionUID = -2302481811822001881L;

    private KKBSignType signType;

    private byte[] signature;

    // type - тип подписи
    @XmlAttribute(name = "type")
    public KKBSignType getSignType() {
	return signType;
    }

    public void setSignType(KKBSignType signType) {
	this.signType = signType;
    }

    // подпись
    @XmlValue
    public byte[] getSignature() {
	return signature;
    }

    public void setSignature(byte[] signature) {
	this.signature = (signature != null && signature.length == 0) ? null : signature;
    }
}
