package com.lapsa.kkb.api;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lapsa.fin.FinCurrency;

public class KKBAuthorization implements Serializable {
    private static final long serialVersionUID = 1769054616339347695L;

    private String orderId;
    private FinCurrency currency;

    private String customerName;
    private String customerMail;
    private String customerPhone;

    private Date paymentsTimestamp;
    private Map<String, KKBAuthorizationPayment> payments = new HashMap<>();

    public double getTotalAmount() {
	double ret = 0;
	for (KKBAuthorizationPayment p : payments.values())
	    ret += p.getAmount();
	return ret;
    }

    // GENERATED

    public String getOrderId() {
	return orderId;
    }

    public String getCustomerName() {
	return customerName;
    }

    public void setCustomerName(String customerName) {
	this.customerName = customerName;
    }

    public String getCustomerMail() {
	return customerMail;
    }

    public void setCustomerMail(String customerMail) {
	this.customerMail = customerMail;
    }

    public String getCustomerPhone() {
	return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
	this.customerPhone = customerPhone;
    }

    public void setOrderId(String orderId) {
	this.orderId = orderId;
    }

    public FinCurrency getCurrency() {
	return currency;
    }

    public void setCurrency(FinCurrency currency) {
	this.currency = currency;
    }

    public Map<String, KKBAuthorizationPayment> getPayments() {
	return payments;
    }

    public void setPayments(Map<String, KKBAuthorizationPayment> payments) {
	this.payments = payments;
    }

    public Date getPaymentsTimestamp() {
	return paymentsTimestamp;
    }

    public void setPaymentsTimestamp(Date paymentsTimestamp) {
	this.paymentsTimestamp = paymentsTimestamp;
    }
}
