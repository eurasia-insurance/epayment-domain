package com.lapsa.kkb.core.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlInlineBinaryData;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "merchant_sign")
public class KKBMerchantSign implements Serializable {
    private static final long serialVersionUID = -9140696247654285305L;

    // type - Тип подписи
    @XmlAttribute(name = "type", required = true)
    private KKBSignType type;

    // подпись
    @XmlValue
    @XmlInlineBinaryData
    private byte[] digest;

    // GENERATED

    public KKBSignType getType() {
	return type;
    }

    public void setType(KKBSignType type) {
	this.type = type;
    }

    public byte[] getDigest() {
	return digest;
    }

    public void setDigest(byte[] digest) {
	this.digest = digest;
    }

}
