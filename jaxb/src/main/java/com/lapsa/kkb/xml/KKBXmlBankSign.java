package com.lapsa.kkb.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
@XmlRootElement(name = "bank_sign")
public class KKBXmlBankSign extends KKBXmlGenericSign implements Serializable {
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
