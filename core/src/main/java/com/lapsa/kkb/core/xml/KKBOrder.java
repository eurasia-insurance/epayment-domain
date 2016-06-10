package com.lapsa.kkb.core.xml;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
public class KKBOrder implements Serializable {

    private static final long serialVersionUID = -2920050474132203303L;

    // order_id - Номер заказа(должен состоять не менее чем из 6 ЧИСЛОВЫХ
    // знаков, максимально -15)

    @XmlAttribute(name = "order_id", required = true)
    private String orderId;

    // amount - сумма заказа,
    @XmlAttribute(name = "amount", required = true)
    private int amount;

    // currency - код валюты оплаты [ 398 - тенге ]
    @XmlAttribute(name = "currency", required = true)
    private int currencyCode;

    @XmlElementRef(required = true)
    private List<KKBDepartment> departments;

    // GENERATED

    public String getOrderId() {
	return orderId;
    }

    public void setOrderId(String orderId) {
	this.orderId = orderId;
    }

    public int getAmount() {
	return amount;
    }

    public void setAmount(int amount) {
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
