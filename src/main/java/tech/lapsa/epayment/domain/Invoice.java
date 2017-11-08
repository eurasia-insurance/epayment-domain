package tech.lapsa.epayment.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.lapsa.fin.FinCurrency;
import com.lapsa.international.localization.LocalizationLanguage;

import tech.lapsa.java.commons.function.MyNumbers;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.java.commons.localization.Localized;
import tech.lapsa.kz.taxpayer.TaxpayerNumber;

public class Invoice extends AEntity {

    private static final long serialVersionUID = 1L;
    private static final int PRIME = 3;

    public static InvoiceBuilder builder() {
	return new InvoiceBuilder();
    }

    public static final class InvoiceBuilder {
	private class Itm {
	    private final String name;
	    private final Integer quantity;
	    private final Double price;

	    private Itm(final String name, final Integer quantity, final Double price) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	    }
	}

	private FinCurrency currency;
	private String consumerEmail;
	private String consumerName;
	private LocalizationLanguage consumerPreferLanguage;
	private TaxpayerNumber consumerTaxpayerNumber;
	private String externalId;
	private List<Itm> itms = new ArrayList<>();

	private InvoiceBuilder() {
	}

	public InvoiceBuilder withCurrencty(final FinCurrency currency) {
	    this.currency = Objects.requireNonNull(currency, "currency");
	    return this;
	}

	public InvoiceBuilder withConsumer(final String consumerName, final String consumerEmail,
		final LocalizationLanguage consumerPreferLanguage, final TaxpayerNumber consumerTaxpayerNumber) {
	    withConsumer(consumerName, consumerEmail, consumerPreferLanguage);
	    this.consumerTaxpayerNumber = MyObjects.requireNonNull(consumerTaxpayerNumber, "consumerTaxpayerNumber");
	    return this;
	}

	public InvoiceBuilder withConsumer(final String consumerName, final String consumerEmail,
		final LocalizationLanguage consumerPreferLanguage) {
	    this.consumerEmail = MyStrings.requireNonEmpty(consumerEmail, "consumerEmail");
	    this.consumerName = MyStrings.requireNonEmpty(consumerName, "consumerName");
	    this.consumerPreferLanguage = MyObjects.requireNonNull(consumerPreferLanguage, "consumerPreferLanguage");
	    return this;
	}

	public InvoiceBuilder withItem(final String name, final Integer quantity, final Double price) {
	    MyStrings.requireNonEmpty(name, "name");
	    MyNumbers.requirePositive(quantity, "quantity");
	    MyNumbers.requirePositive(price, "price");
	    itms.add(new Itm(name, quantity, price));
	    return this;
	}

	public InvoiceBuilder clearItems() {
	    itms = new ArrayList<>();
	    return this;
	}

	public InvoiceBuilder withExternalId(final String externalId) {
	    this.externalId = MyStrings.requireNonEmpty(externalId, "externalId");
	    return this;
	}

	public Invoice build() {
	    final Invoice invoice = new Invoice();
	    invoice.currency = MyObjects.requireNonNull(currency, "currency");
	    invoice.items = MyOptionals.streamOf(itms) //
		    .orElseThrow(() -> new IllegalArgumentException(
			    "An invoice must contains at least one item")) //
		    .map(i -> {
			Item r = new Item();
			r.invoice = invoice;
			r.name = i.name;
			r.price = i.price;
			r.quantity = i.quantity;
			r.invoice = invoice;
			return r;
		    }) //
		    .collect(Collectors.toList());
	    invoice.consumerEmail = MyStrings.requireNonEmpty(consumerEmail, "consumerEmail");
	    invoice.consumerName = MyStrings.requireNonEmpty(consumerName, "consumerName");
	    invoice.consumerPreferLanguage = MyObjects.requireNonNull(consumerPreferLanguage, "consumerPreferLanguage");
	    invoice.consumerTaxpayerNumber = consumerTaxpayerNumber;
	    invoice.externalId = externalId;
	    return invoice;
	}
    }

    @Override
    protected int prime() {
	return PRIME;
    }

    @Override
    public String localized(final LocalizationVariant variant, final Locale locale) {
	final StringBuilder sb = new StringBuilder();

	sb.append(Localization.INVOICE.localized(variant, locale));

	final StringJoiner sj = new StringJoiner(", ", " ", "");
	sj.setEmptyValue("");

	MyOptionals.of(status) //
		.map(Localized.toLocalizedMapper(variant, locale))//
		.map(Localization.FIELD_INVOICE_STATTUS.fieldAsCaptionMapper(variant, locale)) //
		.ifPresent(sj::add);

	return sb.append(sj.toString()) //
		.append(appendEntityId()) //
		.toString();
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

    // consumer

    protected String consumerEmail;

    public String getConsumerEmail() {
	return consumerEmail;
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

    public Invoice paidBy(final APayment payment) {
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