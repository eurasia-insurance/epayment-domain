package com.lapsa.kkb.xml;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
@XmlRootElement(name = "merchant")
public class KKBXmlMerchant implements Serializable {
    private static final long serialVersionUID = -671497891323516467L;

    // cert_id - Серийный номер сертификата
    @XmlAttribute(name = "cert_id")
    private String certificateSerialId;

    // name - имя магазина(сайта)
    @XmlAttribute(name = "name")
    private String name;

    @XmlElementRef
    private List<KKBXmlOrder> orders;

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

    public List<KKBXmlOrder> getOrders() {
	return orders;
    }

    public void setOrders(List<KKBXmlOrder> orders) {
	this.orders = orders;
    }

}
