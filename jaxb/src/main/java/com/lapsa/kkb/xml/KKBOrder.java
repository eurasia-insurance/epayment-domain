package com.lapsa.kkb.xml;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
public class KKBOrder implements Serializable {

    private static final long serialVersionUID = -2920050474132203303L;

    // order_id - Номер заказа(должен состоять не менее чем из 6 ЧИСЛОВЫХ
    // знаков, максимально -15)

    @XmlAttribute(name = "order_id")
    private String orderId;

    // amount - сумма заказа,
    @XmlAttribute(name = "amount")
    @XmlJavaTypeAdapter(KKBAmountXmlAdapter.class)
    private Double amount;

    // currency - код валюты оплаты [ 398 - тенге ]
    @XmlAttribute(name = "currency")
    private int currencyCode;

    @XmlElementRef
    private List<KKBDepartment> departments;

    // GENERATED

    public String getOrderId() {
	return orderId;
    }

    public void setOrderId(String orderId) {
	this.orderId = orderId;
    }

    public double getAmount() {
	return amount;
    }

    public void setAmount(double amount) {
	this.amount = amount;
    }

    public int getCurrencyCode() {
	return currencyCode;
    }

    public void setCurrencyCode(int currencyCode) {
	this.currencyCode = currencyCode;
    }

    public List<KKBDepartment> getDepartments() {
	return departments;
    }

    public void setDepartments(List<KKBDepartment> departments) {
	this.departments = departments;
    }
}
