package com.lapsa.kkb.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lapsa.fin.FinCurrency;
import com.lapsa.kkb.api.KKBFormatException;

public class KKBPaymentOrder implements Serializable {
    private static final long serialVersionUID = 1769054616339347695L;

    private String orderId;
    private FinCurrency currency;

    private Map<String, KKBPaymentOperation> operations = new HashMap<>();

    private KKBPaymentStatus status;

    private Date paymentsTimestamp;
    private KKBPaymentCustomer customer;

    private KKBSignature responseSignature;

    public double getTotalAmount() {
	double ret = 0;
	for (KKBPaymentOperation p : operations.values())
	    ret += p.getAmount();
	return ret;
    }

    @SuppressWarnings("deprecation")
    public void addOperation(KKBPaymentOperation operation) throws KKBFormatException {
	if (operations.containsKey(operation.getMerchantId()))
	    throw new KKBFormatException(
		    String.format("Operation merchangId = '%1$s' already exists", operation.getMerchantId()));
	operations.put(operation.getMerchantId(), operation);
	operation.setPayment(this);
    }

    @SuppressWarnings("deprecation")
    public void removeOperation(KKBPaymentOperation operation) throws KKBFormatException {
	if (!operations.containsKey(operation.getMerchantId()))
	    throw new KKBFormatException(
		    String.format("Operation merchangId = '' is not presents", operation.getMerchantId()));
	operations.remove(operation.getMerchantId());
	operation.setPayment(null);
    }

    public boolean hasOperationMerchant(String merchantId) {
	return operations.containsKey(merchantId);
    }

    public KKBPaymentOperation getOperationToMerchant(String merchantId) {
	return operations.get(merchantId);
    }

    public Collection<KKBPaymentOperation> getOperationsList() {
	return operations.values();
    }

    // GENERATED

    public String getOrderId() {
	return orderId;
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

    public Date getPaymentsTimestamp() {
	return paymentsTimestamp;
    }

    public void setPaymentsTimestamp(Date paymentsTimestamp) {
	this.paymentsTimestamp = paymentsTimestamp;
    }

    @Deprecated
    public Map<String, KKBPaymentOperation> getOperations() {
	return operations;
    }

    @Deprecated
    public void setOperations(Map<String, KKBPaymentOperation> operations) {
	this.operations = operations;
    }

    public KKBPaymentCustomer getCustomer() {
	return customer;
    }

    public void setCustomer(KKBPaymentCustomer customer) {
	this.customer = customer;
    }

    public KKBPaymentStatus getStatus() {
	return status;
    }

    public void setStatus(KKBPaymentStatus status) {
	this.status = status;
    }

    public KKBSignature getResponseSignature() {
	return responseSignature;
    }

    public void setResponseSignature(KKBSignature responseSignature) {
	this.responseSignature = responseSignature;
    }
}
