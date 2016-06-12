package com.lapsa.kkb.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bank")
public class KKBXmlBank implements Serializable {
    private static final long serialVersionUID = -5468834860872828233L;

    @XmlAttribute(name = "name")
    private String name;

    @XmlElementRef
    private KKBXmlCustomer customer;

    @XmlElementRef
    private KKBXmlCustomerSign customerSign;

    @XmlElementRef
    private KKBResults results;

    // GENERATED

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public KKBXmlCustomer getCustomer() {
	return customer;
    }

    public void setCustomer(KKBXmlCustomer customer) {
	this.customer = customer;
    }

    public KKBXmlCustomerSign getCustomerSign() {
	return customerSign;
    }

    public void setCustomerSign(KKBXmlCustomerSign customerSign) {
	this.customerSign = customerSign;
    }

    public KKBResults getResults() {
	return results;
    }

    public void setResults(KKBResults results) {
	this.results = results;
    }
}
