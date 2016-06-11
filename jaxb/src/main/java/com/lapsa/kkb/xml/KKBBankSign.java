package com.lapsa.kkb.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bank_sign")
public class KKBBankSign extends KKBBaseSign implements Serializable {
    private static final long serialVersionUID = -4925501165429637554L;

    // cert_id - серийный номер сертификата
    @XmlAttribute(name = "cert_id")
    private String certificateSerialId;

    public String getCertificateSerialId() {
	return certificateSerialId;
    }

    public void setCertificateSerialId(String certificateSerialId) {
	this.certificateSerialId = certificateSerialId;
    }
}
