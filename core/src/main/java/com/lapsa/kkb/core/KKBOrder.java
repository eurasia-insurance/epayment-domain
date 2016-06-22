package com.lapsa.kkb.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.lapsa.fin.FinCurrency;

public class KKBOrder extends BaseDomain {
    private static final long serialVersionUID = 8043063701133705294L;
    private static final int PRIME = 5;
    private static final int MULTIPLIER = 5;

    private String id;

    private double amount;

    private FinCurrency currency;

    private List<KKBOrderItem> items;

    private KKBPaymentStatus status;

    private Date created;

    private Date updated;

    private Date closed;

    private KKBCartDocument lastCart;

    private KKBPaymentRequestDocument lastRequest;

    private KKBPaymentResponseDocument lastResponse;

    @Override
    protected int getPrime() {
	return PRIME;
    }

    @Override
    protected int getMultiplier() {
	return MULTIPLIER;
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder(getPrime(), getMultiplier())
		.append(id)
		.append(amount)
		.append(currency)
		.append(items)
		.append(status)
		.append(created)
		.append(updated)
		.append(closed)
		.append(lastCart)
		.append(lastRequest)
		.append(lastResponse)
		.toHashCode();
    }

    @Override
    public boolean equals(Object other) {
	if (other == null || other.getClass() != getClass())
	    return false;
	if (other == this)
	    return true;
	KKBOrder that = (KKBOrder) other;
	return new EqualsBuilder()
		.append(id, that.id)
		.append(amount, that.amount)
		.append(currency, that.currency)
		.append(items, that.items)
		.append(status, that.status)
		.append(created, that.created)
		.append(updated, that.updated)
		.append(closed, that.closed)
		.append(lastCart, that.lastCart)
		.append(lastRequest, that.lastRequest)
		.append(lastResponse, that.lastResponse)
		.isEquals();
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

    public void setItems(List<KKBOrderItem> items) {
	this.items = items;
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

}
