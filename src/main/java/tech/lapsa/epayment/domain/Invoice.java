package tech.lapsa.epayment.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import com.lapsa.fin.FinCurrency;
import com.lapsa.international.localization.LocalizationLanguage;

import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.kz.taxpayer.TaxpayerNumber;

public class Invoice extends AEntity<Integer> {

    private static final long serialVersionUID = 1L;

    public Invoice() {
	super(3);
    }

    @Override
    public String localized(LocalizationVariant variant, Locale locale) {
	// TODO Auto-generated method stub
	return Invoice.class.getName();
    }

    // created

    protected Instant created = Instant.now();

    public Instant getCreated() {
	return created;
    }

    protected InvoiceStatus status = InvoiceStatus.READY;

    public InvoiceStatus getStatus() {
	return status;
    }

    // currency

    protected FinCurrency currency;

    public FinCurrency getCurrency() {
	return currency;
    }

    // items

    protected List<Item> items;

    public List<Item> getItems() {
	return Collections.unmodifiableList(items);
    }

    public void addItem(Item item) {
	MyObjects.requireNonNull(item, "item");
	if (item.invoice != null)
	    item.invoice.removeItem(item);
	if (items == null)
	    items = new ArrayList<>();
	items.add(item);
	item.invoice = this;
    }

    public void removeItem(Item item) {
	if (items != null && items.contains(MyObjects.requireNonNull(item, "item"))) {
	    items.remove(item);
	    item.invoice = null;
	}
    }

    // payerEmail

    protected String payerEmail;

    public String getPayerEmail() {
	return payerEmail;
    }

    // consumerName

    protected String consumerName;

    public String getConsumerName() {
	return consumerName;
    }

    // consumerTaxpayerNumber

    protected TaxpayerNumber consumerTaxpayerNumber;

    public TaxpayerNumber getConsumerTaxpayerNumber() {
	return consumerTaxpayerNumber;
    }

    // consumerPreferLanguage

    protected LocalizationLanguage consumerPreferLanguage;

    public LocalizationLanguage getConsumerPreferLanguage() {
	return consumerPreferLanguage;
    }

    // externalId

    protected String externalId;

    public String getExternalId() {
	return externalId;
    }

    // payment

    protected APayment payment;

    public APayment getPayment() {
	return payment;
    }

    // OTHERS

    public Double getAmount() {
	return MyOptionals.streamOf(items)
		.orElseGet(Stream::empty)
		.mapToDouble(Item::getTotal)
		.sum();
    }

    // controllers

    public Invoice paidBy(APayment payment) {
	if (status != InvoiceStatus.READY)
	    throw new IllegalStateException("Invoice is not ready. It could be or expired or paid already");
	MyObjects.requireNonNull(payment, "payment");
	MyObjects.requireNull(payment.forInvoice, "forInvoice");
	MyObjects.requireNull(this.payment, "payment");

	status = InvoiceStatus.PAID;
	this.payment = payment;
	payment.forInvoice = this;

	return this;
    }
}
