package com.lapsa.kkb.core.xml;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "merchant")
public class KKBMerchant implements Serializable {
    private static final long serialVersionUID = -671497891323516467L;

    // cert_id - Серийный номер сертификата

    @XmlAttribute(name = "cert_id", required = true)
    private String certificateSerialId;

    // name - имя магазина(сайта)
    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlElementRef(required = true)
    private List<KKBOrder> orders;

    // GENERATED

    public String getCertificateSerialId() {
	return certificateSerialId;
    }

    public void setCertificateSerialId(String certificateSerialId) {
	this.certificateSerialId = certificateSerialId;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public List<KKBOrder> getOrders() {
	return orders;
    }

    public void setOrders(List<KKBOrder> orders) {
	this.orders = orders;
    }

}
