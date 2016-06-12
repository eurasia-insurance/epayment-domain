package com.lapsa.kkb.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bank")
public class KKBBank implements Serializable {
    private static final long serialVersionUID = -5468834860872828233L;

    @XmlAttribute(name = "name")
    private String name;

    @XmlElementRef
    private KKBCustomer customer;

    @XmlElementRef
    private KKBCustomerSign customerSign;

    @XmlElementRef
    private KKBResults results;

    // GENERATED

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public KKBCustomer getCustomer() {
	return customer;
    }

    public void setCustomer(KKBCustomer customer) {
	this.customer = customer;
    }

    public KKBCustomerSign getCustomerSign() {
	return customerSign;
    }

    public void setCustomerSign(KKBCustomerSign customerSign) {
	this.customerSign = customerSign;
    }

    public KKBResults getResults() {
	return results;
    }

    public void setResults(KKBResults results) {
	this.results = results;
    }
}
