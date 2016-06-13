package com.lapsa.kkb.api;

import com.lapsa.fin.FinCurrency;

public class KKBAuthorizationRequest {

    private String orderId;
    private double amount;
    private FinCurrency currency;

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

    public FinCurrency getCurrency() {
	return currency;
    }

    public void setCurrency(FinCurrency currency) {
	this.currency = currency;
    }

}
