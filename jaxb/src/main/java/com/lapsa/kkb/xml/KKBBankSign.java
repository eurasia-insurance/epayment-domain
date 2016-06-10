package com.lapsa.kkb.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bank_sign")
public class KKBBankSign implements Serializable {
    private static final long serialVersionUID = -4925501165429637554L;

    // cert_id - серийный номер сертификата
    @XmlAttribute(name = "cert_id")
    private String certificateSerialId;

    // type - тип подписи
    @XmlAttribute(name = "type")
    private KKBSignType signType;

    // подпись
    @XmlValue
    private byte[] signature;

    public String getCertificateSerialId() {
	return certificateSerialId;
    }

    public void setCertificateSerialId(String certificateSerialId) {
	this.certificateSerialId = certificateSerialId;
    }

    public KKBSignType getSignType() {
	return signType;
    }

    public void setSignType(KKBSignType signType) {
	this.signType = signType;
    }

    public byte[] getSignature() {
	return signature;
    }

    public void setSignature(byte[] signature) {
	this.signature = signature;
    }
}
