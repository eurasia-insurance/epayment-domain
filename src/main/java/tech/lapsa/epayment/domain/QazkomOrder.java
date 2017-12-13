package tech.lapsa.epayment.domain;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.function.Predicate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tech.lapsa.epayment.domain.Exceptions.IsNotPaidException;
import tech.lapsa.epayment.domain.Exceptions.IsPaidException;
import tech.lapsa.epayment.qazkom.xml.bind.XmlDocumentCart;
import tech.lapsa.epayment.qazkom.xml.bind.XmlDocumentOrder;
import tech.lapsa.java.commons.function.MyCollections;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyNumbers;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.java.commons.localization.Localizeds;
import tech.lapsa.patterns.domain.HashCodePrime;

@Entity
@Table(name = "QAZKOM_ORDER")
@HashCodePrime(11)
public class QazkomOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static String generateNumber() {
	return String.valueOf(Math.abs(UUID.randomUUID().getLeastSignificantBits() / 10000));
    }

    public static String generateNumber(final Predicate<String> numberIsUniqueTest)
	    throws IllegalArgumentException, NumberOfAttemptsExceedException {
	MyObjects.requireNonNull(numberIsUniqueTest, "numberIsUniqueTest");
	final int NUMBER_OF_ATTEMPTS = 100;
	int attempt = 0;
	String number;
	do {
	    number = generateNumber();
	    if (attempt++ > NUMBER_OF_ATTEMPTS)
		throw MyExceptions.format(NumberOfAttemptsExceedException::new,
			"The number of attempts is exceed the limit (%1$d) while generating the unique number",
			NUMBER_OF_ATTEMPTS);
	} while (!numberIsUniqueTest.test(number));
	return number;
    }

    public static QazkomOrderBuilder builder() {
	return new QazkomOrderBuilder();
    }

    public static final class QazkomOrderBuilder implements Serializable {

	private static final long serialVersionUID = 1L;

	private String orderNumber;
	private Invoice forInvoice;

	private String merchantId;
	private String merchantName;
	private PrivateKey merchantKey;
	private X509Certificate merchantCertificate;

	private QazkomOrderBuilder() {
	}

	public QazkomOrderBuilder forInvoice(final Invoice forInvoice) throws IllegalArgumentException {
	    this.forInvoice = MyObjects.requireNonNull(forInvoice, "forInvoice");
	    return this;
	}

	public QazkomOrderBuilder withNumber(final String orderNumber) throws IllegalArgumentException {
	    this.orderNumber = MyStrings.requireNonEmpty(orderNumber, "orderNumber");
	    return this;
	}

	public QazkomOrderBuilder withGeneratedNumber() {
	    orderNumber = null;
	    return this;
	}

	public QazkomOrderBuilder withMerchant(final String merchantId, final String merchantName,
		final X509Certificate merchantCertificate,
		final PrivateKey merchantKey) throws IllegalArgumentException {
	    this.merchantId = MyStrings.requireNonEmpty(merchantId, "merchantId");
	    this.merchantName = MyStrings.requireNonEmpty(merchantName, "merchantName");
	    this.merchantKey = MyObjects.requireNonNull(merchantKey, "merchantKey");
	    this.merchantCertificate = MyObjects.requireNonNull(merchantCertificate, "merchantCertificate");
	    return this;
	}

	public QazkomOrder build() throws IllegalArgumentException, NonUniqueNumberException {
	    return build(null);
	}

	public QazkomOrder build(final Predicate<String> numberIsUniqueTest)
		throws IllegalArgumentException, NumberOfAttemptsExceedException, NonUniqueNumberException {
	    final QazkomOrder result = new QazkomOrder();

	    if (MyStrings.empty(orderNumber))
		// using generated value
		result.orderNumber = MyObjects.nonNull(numberIsUniqueTest) //
			? generateNumber(numberIsUniqueTest) //
			: generateNumber();
	    else {
		// using user value
		if (MyObjects.nonNull(numberIsUniqueTest) && !numberIsUniqueTest.test(orderNumber))
		    throw new NonUniqueNumberException(String.format("The number is non-unique (%1$s)", orderNumber));
		result.orderNumber = orderNumber;
	    }

	    result.forInvoice = MyObjects.requireNonNull(forInvoice, "forInvoice");
	    result.amount = MyNumbers.requirePositive(forInvoice.getAmount(), "forInvoice.getAmount");
	    result.currency = MyObjects.requireNonNull(forInvoice.currency, "forInvoice.currency");

	    result.orderDoc = new QazkomXmlDocument( //
		    XmlDocumentOrder.builder() //
			    .withOrderNumber(result.orderNumber) //
			    .withAmount(result.amount) //
			    .withCurrency(result.currency) //
			    .withMerchchant(MyStrings.requireNonEmpty(merchantId, "merchantId"),
				    MyStrings.requireNonEmpty(merchantName, "merchantName")) //
			    .signWith(MyObjects.requireNonNull(merchantKey, "merchantKey"),
				    MyObjects.requireNonNull(merchantCertificate, "merchantCertificate")) //
			    .build() //
			    .getRawXml(),
		    QazkomXmlDocument.DocumentType.ORDER);

	    result.cartDoc = new QazkomXmlDocument(XmlDocumentCart.builder() //
		    .withItems(MyCollections.requireNonEmpty(forInvoice.getItems(), "forInvoice.getItems"),
			    Item::getName, Item::getQuantity, Item::getTotal) //
		    .build() //
		    .getRawXml(), QazkomXmlDocument.DocumentType.CART);

	    return result;
	}
    }

    @Override
    public String localized(final LocalizationVariant variant, final Locale locale) {
	final StringBuilder sb = new StringBuilder();

	sb.append(Localization.QAZKOMORDER_NAME.localized(variant, locale));

	final StringJoiner sj = new StringJoiner(", ", " ", "");
	sj.setEmptyValue("");

	MyOptionals.of(orderNumber) //
		.map(Localization.FIELD_NUMBER.fieldAsCaptionMapper(variant, locale)) //
		.ifPresent(sj::add);

	MyOptionals.of(created) //
		.map(Localizeds.instantMapper(locale)) //
		.map(Localization.FIELD_CREATED.fieldAsCaptionMapper(variant, locale)) //
		.ifPresent(sj::add);

	if (amount != null && currency != null) {
	    final NumberFormat nf = NumberFormat.getCurrencyInstance();
	    nf.setCurrency(currency);
	    sj.add(Localization.PAYMENT_FIELD_AMOUNT.fieldAsCaptionMapper(variant, locale)
		    .apply(nf.format(amount)));
	}

	return sb.append(sj.toString()) //
		.append(appendEntityId()) //
		.toString();
    }

    // created

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    protected Instant created = Instant.now();

    public Instant getCreated() {
	return created;
    }

    // orderNumber

    @Basic
    @Column(name = "ORDER_NUMBER", unique = true)
    protected String orderNumber;

    public String getOrderNumber() {
	return orderNumber;
    }

    // amount

    @Basic
    @Column(name = "AMOUNT")
    protected Double amount;

    public Double getAmount() {
	return amount;
    }

    // currency

    @Basic
    @Column(name = "CURRENCY")
    protected Currency currency;

    public Currency getCurrency() {
	return currency;
    }

    // forInvoice

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "INVOICE_ID")
    protected Invoice forInvoice;

    public Invoice getForInvoice() {
	return forInvoice;
    }

    public Optional<Invoice> optionalForInvoice() {
	return MyOptionals.of(getForInvoice());
    }

    // payment

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PAYMENT_ID")
    protected QazkomPayment payment;

    public QazkomPayment getPayment() {
	return payment;
    }

    public Optional<QazkomPayment> optionalPayment() {
	return MyOptionals.of(getPayment());
    }

    public boolean isPaid() {
	return optionalPayment().isPresent();
    }

    public QazkomOrder requireNotPaid() throws IllegalStateException {
	if (isPaid())
	    throw MyExceptions.illegalStateFormat(IsPaidException::new, "Is paid '%1$s'", this);
	return this;
    }

    public QazkomOrder requirePaid() throws IllegalStateException {
	if (!isPaid())
	    throw MyExceptions.illegalStateFormat(IsNotPaidException::new, "Is not paid yet '%1$s'", this);
	return this;
    }

    // orderDoc

    @ManyToOne
    @JoinColumn(name = "ORDER_DOC_ID")
    protected QazkomXmlDocument orderDoc;

    public QazkomXmlDocument getOrderDoc() {
	return orderDoc;
    }

    // cartDoc

    @ManyToOne
    @JoinColumn(name = "CART_DOC_ID")
    protected QazkomXmlDocument cartDoc;

    public QazkomXmlDocument getCartDoc() {
	return cartDoc;
    }

    // items

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", orphanRemoval = true)
    protected List<QazkomError> errors;

    public List<QazkomError> getErrors() {
	return MyCollections.unmodifiableOrEmptyList(errors);
    }

    // controllers

    @Override
    public void unlazy() {
	MyOptionals.of(getCartDoc()).ifPresent(BaseEntity::unlazy);
	MyOptionals.of(getOrderDoc()).ifPresent(BaseEntity::unlazy);
	MyOptionals.of(getPayment()).ifPresent(BaseEntity::unlazy);
	MyOptionals.of(getForInvoice()).ifPresent(BaseEntity::unlazy);
	getErrors();
    }

    public void attachError(final QazkomError error) throws IllegalArgumentException, IllegalStateException {
	MyObjects.requireNonNull(error, "order");

	MyStrings.requireEqualsMsg(orderNumber, error.orderNumber,
		"Error order number and order number are not the same");

	if (error.optionalOrder().isPresent())
	    throw MyExceptions.illegalStateFormat("Error has order attached already");

	errors.add(error);
	error.order = this;
    }

    public void paidBy(final QazkomPayment payment) throws IllegalArgumentException, IllegalStateException {

	MyObjects.requireNonNull(payment, "payment");

	MyStrings.requireEqualsMsg(orderNumber, payment.orderNumber,
		"Qazkom order number and payment order number are not the same");
	MyNumbers.requireEqualsMsg(getAmount(), payment.getAmount(),
		"Qazkom order amount and payment amount are not the same");

	if (payment.optionalOrder().isPresent())
	    throw MyExceptions.illegalStateFormat("Payment has order attached already");

	if (optionalPayment().isPresent())
	    throw MyExceptions.illegalStateFormat("Order has payment attached already");

	// TODO FEAUTURE : Need to implement more Qazkom validation points

	this.payment = payment;
	payment.order = this;
    }
}
