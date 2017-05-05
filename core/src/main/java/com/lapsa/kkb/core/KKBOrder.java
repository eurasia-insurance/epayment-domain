package com.lapsa.kkb.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lapsa.fin.FinCurrency;
import com.lapsa.localization.LocalizationLanguage;

public class KKBOrder extends KKBBaseDomain {
    private static final long serialVersionUID = 8043063701133705294L;
    private static final int PRIME = 5;
    private static final int MULTIPLIER = 5;

    private String id;

    private double amount;

    private FinCurrency currency;

    private List<KKBOrderItem> items;

    private String consumerEmail;

    private LocalizationLanguage consumerLanguage;
    private String consumerName;

    private KKBPaymentStatus status;

    private Date created;

    private Date updated;

    private Date closed;

    private KKBCartDocument lastCart;

    private KKBPaymentRequestDocument lastRequest;

    private KKBPaymentResponseDocument lastResponse;

    private String externalId;

    @Override
    protected int getPrime() {
	return PRIME;
    }

    @Override
    protected int getMultiplier() {
	return MULTIPLIER;
    }

    public void addItem(KKBOrderItem item) {
	if (item == null)
	    throw new NullPointerException("Value must not be null");
	if (item.getOrder() != null)
	    item.getOrder().removeItem(item);
	if (items == null)
	    items = new ArrayList<>();
	items.add(item);
	item.setOrder(this);

	calculateTotalAmount();
    }

    public void removeItem(KKBOrderItem item) {
	if (item == null)
	    throw new NullPointerException("Value must not be null");
	if (items == null) // nothing to remove from
	    return;
	items.remove(item);
	item.setOrder(null);

	calculateTotalAmount();
    }

    @SuppressWarnings("deprecation")
    public void setLastCart(KKBCartDocument lastCart) {
	if (lastCart == null)
	    throw new NullPointerException("Value must not be null");
	lastCart.setOrder(this);
	this.lastCart = lastCart;
    }

    @SuppressWarnings("deprecation")
    public void setLastResponse(KKBPaymentResponseDocument lastResponse) {
	if (lastResponse == null)
	    throw new NullPointerException("Value must not be null");
	lastResponse.setOrder(this);
	this.lastResponse = lastResponse;
    }

    @SuppressWarnings("deprecation")
    public void setLastRequest(KKBPaymentRequestDocument lastRequest) {
	if (lastRequest == null)
	    throw new NullPointerException("Value must not be null");
	lastRequest.setOrder(this);
	this.lastRequest = lastRequest;
    }

    public void calculateTotalAmount() {
	amount = 0d;
	if (items == null)
	    return;
	for (KKBOrderItem item : items)
	    amount += item.getCost();
    }

    // GENERATED

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public double getAmount() {
	return amount;
    }

    @Deprecated
    public void setAmount(double amount) {
	this.amount = amount;
    }

    public FinCurrency getCurrency() {
	return currency;
    }

    public void setCurrency(FinCurrency currency) {
	this.currency = currency;
    }

    public List<KKBOrderItem> getItems() {
	return items;
    }

    @Deprecated
    public void setItems(List<KKBOrderItem> items) {
	this.items = items;
    }

    public String getConsumerEmail() {
	return consumerEmail;
    }

    public void setConsumerEmail(String consumerEmail) {
	this.consumerEmail = consumerEmail;
    }

    public LocalizationLanguage getConsumerLanguage() {
	return consumerLanguage;
    }

    public void setConsumerLanguage(LocalizationLanguage consumerLanguage) {
	this.consumerLanguage = consumerLanguage;
    }

    public KKBPaymentStatus getStatus() {
	return status;
    }

    public void setStatus(KKBPaymentStatus status) {
	this.status = status;
    }

    public Date getCreated() {
	return created;
    }

    public void setCreated(Date created) {
	this.created = created;
    }

    public Date getUpdated() {
	return updated;
    }

    public void setUpdated(Date updated) {
	this.updated = updated;
    }

    public Date getClosed() {
	return closed;
    }

    public void setClosed(Date closed) {
	this.closed = closed;
    }

    public KKBCartDocument getLastCart() {
	return lastCart;
    }

    public KKBPaymentRequestDocument getLastRequest() {
	return lastRequest;
    }

    public KKBPaymentResponseDocument getLastResponse() {
	return lastResponse;
    }

    public String getExternalId() {
	return externalId;
    }

    public void setExternalId(String externalId) {
	this.externalId = externalId;
    }

    public void setConsumerName(String consumerName) {
	this.consumerName = consumerName;
    }

    public String getConsumerName() {
	return consumerName;
    }
}
