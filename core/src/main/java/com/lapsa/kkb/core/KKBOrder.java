package com.lapsa.kkb.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lapsa.fin.FinCurrency;

public class KKBOrder extends BaseDomain {
    private static final long serialVersionUID = 8043063701133705294L;

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

    @Override
    public int hashCode() {
	return this.getClass().hashCode()
		* (id != null ? id.hashCode() : super.instanceUUID.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
	return obj != null
		&& this.getClass().isInstance(obj)
		&& ((id == null && instanceUUID.equals(this.getClass().cast(obj).instanceUUID))
			|| (id != null && getId().equals((this.getClass().cast(obj)).getId())));
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
