package com.lapsa.kkb.core;

import static com.lapsa.kkb.core.DisplayNameElements.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.stream.Stream;

import com.lapsa.commons.function.MyObjects;
import com.lapsa.commons.function.MyOptionals;
import com.lapsa.fin.FinCurrency;
import com.lapsa.international.localization.LocalizationLanguage;

public class KKBOrder extends KKBBaseDomain {
    private static final long serialVersionUID = 1L;
    private static final int PRIME = 5;
    private static final int MULTIPLIER = 5;

    public KKBOrder() {
    }

    public KKBOrder(String id) {
	this.id = id;
    }

    @Override
    protected int getPrime() {
	return PRIME;
    }

    @Override
    protected int getMultiplier() {
	return MULTIPLIER;
    }

    protected String id;

    protected double amount;

    protected FinCurrency currency;

    protected List<KKBOrderItem> items = new ArrayList<>();

    protected String consumerEmail;

    protected LocalizationLanguage consumerLanguage;
    protected String consumerName;

    protected KKBPaymentStatus status;

    protected Instant created;

    protected Instant updated;

    protected Instant closed;

    protected Instant paid;

    protected String paymentReference;

    protected KKBCartDocument lastCart;

    protected KKBPaymentRequestDocument lastRequest;

    protected KKBPaymentResponseDocument lastResponse;

    protected String externalId;

    public void addItem(KKBOrderItem item) {
	MyObjects.requireNonNull(item, "item");
	if (item.getOrder() != null)
	    item.getOrder().removeItem(item);
	if (items == null)
	    items = new ArrayList<>();
	items.add(item);
	item.setOrder(this);
	calculateTotalAmount();
    }

    public void removeItem(KKBOrderItem item) {
	MyObjects.requireNonNull(item, "item");
	items.remove(item);
	item.setOrder(null);
	calculateTotalAmount();
    }

    @Deprecated
    public void setLastCart(KKBCartDocument lastCart) {
	MyObjects.requireNonNull(lastCart, "lastCart");
	lastCart.setOrder(this);
	this.lastCart = lastCart;
    }

    @Deprecated
    public void setLastResponse(KKBPaymentResponseDocument lastResponse) {
	MyObjects.requireNonNull(lastResponse, "lastResponse");
	lastResponse.setOrder(this);
	this.lastResponse = lastResponse;
    }

    @Deprecated
    public void setLastRequest(KKBPaymentRequestDocument lastRequest) {
	MyObjects.requireNonNull(lastRequest, "lastRequest");
	lastRequest.setOrder(this);
	this.lastRequest = lastRequest;
    }

    void calculateTotalAmount() {
	amount = MyOptionals.streamOf(items)
		.orElseGet(Stream::empty)
		.mapToDouble(KKBOrderItem::getTotal)
		.sum();
    }

    @Override
    public String displayName(DisplayNameVariant variant, Locale locale) {
	StringBuilder sb = new StringBuilder();

	sb.append(ORDER.displayName(variant, locale));

	StringJoiner sj = new StringJoiner(", ", " ", "");
	sj.setEmptyValue("");

	MyOptionals.of(created) //
		.map(DisplayNames.instantMapper(locale) //
			.andThen(FIELD_CREATED.fieldAsCaptionMapper(variant, locale))) //
		.ifPresent(sj::add);

	return sb.append(sj.toString()) //
		.append(KKBBaseEntity.appendEntityId(id)) //
		.toString();
    }

    // GENERATED

    public double getAmount() {
	return amount;
    }

    protected void setAmount(double amount) {
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

    protected void setItems(List<KKBOrderItem> items) {
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

    public Instant getCreated() {
	return created;
    }

    public void setCreated(Instant created) {
	this.created = created;
    }

    public Instant getUpdated() {
	return updated;
    }

    public void setUpdated(Instant updated) {
	this.updated = updated;
    }

    public Instant getClosed() {
	return closed;
    }

    public void setClosed(Instant closed) {
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

    public Instant getPaid() {
	return paid;
    }

    public void setPaid(Instant paid) {
	this.paid = paid;
    }

    public String getPaymentReference() {
	return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
	this.paymentReference = paymentReference;
    }

    public String getId() {
	return id;
    }

    protected void setId(String id) {
	this.id = id;
    }
}
