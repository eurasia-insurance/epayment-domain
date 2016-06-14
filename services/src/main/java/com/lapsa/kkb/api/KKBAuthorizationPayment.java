package com.lapsa.kkb.api;

import java.io.Serializable;

public class KKBAuthorizationPayment implements Serializable {
    private static final long serialVersionUID = 214150764325937287L;

    private double amount;
    private String merchantId;
    private KKBAuthorizationPaymentResult result;

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

    public KKBAuthorizationPaymentResult getResult() {
	return result;
    }

    public void setResult(KKBAuthorizationPaymentResult result) {
	this.result = result;
    }
}
