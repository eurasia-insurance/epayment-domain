package com.lapsa.kkb.core;

import java.io.Serializable;

public class KKBPaymentOperation implements Serializable {
    private static final long serialVersionUID = 214150764325937287L;

    private double amount;
    private String merchantId;
    private KKBPaymentOrder payment;

    private KKBPaymentOperationResult result;

    public double getAmount() {
	return amount;
    }

    public void setAmount(double amount) {
	this.amount = amount;
    }

    public String getMerchantId() {
	return merchantId;
    }

    public void setMerchantId(String merchantId) {
	this.merchantId = merchantId;
    }

    public KKBPaymentOperationResult getResult() {
	return result;
    }

    public void setResult(KKBPaymentOperationResult result) {
	this.result = result;
    }

    public KKBPaymentOrder getPayment() {
	return payment;
    }

    @Deprecated
    public void setPayment(KKBPaymentOrder payment) {
	this.payment = payment;
    }

}
