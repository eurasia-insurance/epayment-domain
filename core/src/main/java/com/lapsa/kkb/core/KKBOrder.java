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

    private KKBCart cart;

    private List<KKBPaymentRequest> requests;

    private List<KKBPaymentResponse> responses;

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

    public void addRequest(KKBPaymentRequest request) {
	if (request == null)
	    throw new NullPointerException("Value must not be null");
	if (request.getOrder() != null)
	    request.getOrder().removeRequest(request);
	if (requests == null)
	    requests = new ArrayList<>();
	requests.add(request);
	request.setOrder(this);
    }

    public void removeRequest(KKBPaymentRequest request) {
	if (request == null)
	    throw new NullPointerException("Value must not be null");
	if (requests == null) // nothing to remove from
	    return;
	requests.remove(request);
	request.setOrder(null);
    }

    public void addResponse(KKBPaymentResponse response) {
	if (response == null)
	    throw new NullPointerException("Value must not be null");
	if (response.getOrder() != null)
	    response.getOrder().removeResponse(response);
	if (responses == null)
	    responses = new ArrayList<>();
	responses.add(response);
	response.setOrder(this);
    }

    public void removeResponse(KKBPaymentResponse response) {
	if (response == null)
	    throw new NullPointerException("Value must not be null");
	if (responses == null) // nothing to remove from
	    return;
	responses.remove(response);
	response.setOrder(null);
    }

    public void setCart(KKBCart cart) {
	if (cart == null)
	    throw new NullPointerException("Value must not be null");
	if (cart.getOrder() != null)
	    cart.getOrder().setCart(null);
	cart.setOrder(this);
	this.cart = cart;
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

    public List<KKBPaymentRequest> getRequests() {
	return requests;
    }

    public void setRequests(List<KKBPaymentRequest> requests) {
	this.requests = requests;
    }

    public List<KKBPaymentResponse> getResponses() {
	return responses;
    }

    public void setResponses(List<KKBPaymentResponse> responses) {
	this.responses = responses;
    }

    public KKBCart getCart() {
	return cart;
    }

    // PRIVATE

    private void calculateTotalAmount() {
	amount = 0d;
	if (items == null)
	    return;
	for (KKBOrderItem item : items)
	    amount += item.getCost();
    }
}
