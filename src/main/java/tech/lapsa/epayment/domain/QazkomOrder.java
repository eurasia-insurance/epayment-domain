package tech.lapsa.epayment.domain;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.function.Predicate;

import com.lapsa.fin.FinCurrency;

import tech.lapsa.epayment.qazkom.xml.bind.XmlDocumentCart;
import tech.lapsa.epayment.qazkom.xml.bind.XmlDocumentOrder;
import tech.lapsa.java.commons.function.MyCollections;
import tech.lapsa.java.commons.function.MyNumbers;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.java.commons.localization.Localizeds;

public class QazkomOrder extends AEntity {

    private static final long serialVersionUID = 1L;
    private static final int PRIME = 11;

    public static String generateNumber() {
	return String.valueOf(Math.abs(UUID.randomUUID().getLeastSignificantBits() / 10000));
    }

    public static String generateNumber(final Predicate<String> numberIsUniqueTest)
	    throws NumberOfAttemptsExceedException {
	MyObjects.requireNonNull(numberIsUniqueTest, "numberIsUniqueTest");
	final int NUMBER_OF_ATTEMPTS = 100;
	int attempt = 0;
	String number;
	do {
	    number = generateNumber();
	    if (attempt++ > NUMBER_OF_ATTEMPTS)
		throw new NumberOfAttemptsExceedException(
			String.format(
				"The number of attempts is exceed the limit (%1$d) while generating the unique number",
				NUMBER_OF_ATTEMPTS));
	} while (!numberIsUniqueTest.test(number));
	return number;
    }

    public static QazkomOrderBuilder builder() {
	return new QazkomOrderBuilder();
    }

    public static final class QazkomOrderBuilder {
	private String orderNumber;
	private Invoice forInvoice;

	private String merchantId;
	private String merchantName;
	private PrivateKey merchantKey;
	private X509Certificate merchantCertificate;
	private Predicate<String> numberIsUniqueTest;

	private QazkomOrderBuilder() {
	}

	public QazkomOrderBuilder forInvoice(final Invoice forInvoice) {
	    this.forInvoice = MyObjects.requireNonNull(forInvoice, "forInvoice");
	    return this;
	}

	public QazkomOrderBuilder withNumber(final String orderNumber) {
	    this.orderNumber = MyStrings.requireNonEmpty(orderNumber, "orderNumber");
	    this.numberIsUniqueTest = null;
	    return this;
	}

	public QazkomOrderBuilder withNumber(final String orderNumber, final Predicate<String> numberIsUniqueTest) {
	    this.orderNumber = MyStrings.requireNonEmpty(orderNumber, "orderNumber");
	    this.numberIsUniqueTest = MyObjects.requireNonNull(numberIsUniqueTest, "numberIsUniqueTest");
	    return this;
	}

	public QazkomOrderBuilder withGeneratedNumber(final Predicate<String> numberIsUniqueTest) {
	    this.orderNumber = null;
	    this.numberIsUniqueTest = MyObjects.requireNonNull(numberIsUniqueTest, "numberIsUniqueTest");
	    return this;
	}

	public QazkomOrderBuilder withGeneratedNumber() {
	    this.orderNumber = null;
	    this.numberIsUniqueTest = null;
	    return this;
	}

	public QazkomOrderBuilder withMerchant(final String merchantId, final String merchantName,
		final X509Certificate merchantCertificate,
		final PrivateKey merchantKey) {
	    this.merchantId = MyStrings.requireNonEmpty(merchantId, "merchantId");
	    this.merchantName = MyStrings.requireNonEmpty(merchantName, "merchantName");
	    this.merchantKey = MyObjects.requireNonNull(merchantKey, "merchantKey");
	    this.merchantCertificate = MyObjects.requireNonNull(merchantCertificate, "merchantCertificate");
	    return this;
	}

	public QazkomOrder build() {
	    final QazkomOrder result = new QazkomOrder();

	    if (MyStrings.empty(orderNumber)) {
		// using generated value
		result.orderNumber = MyObjects.nonNull(numberIsUniqueTest) //
			? generateNumber(numberIsUniqueTest) //
			: generateNumber();
	    } else {
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
			    .getRawXml());

	    result.cartDoc = new QazkomXmlDocument(XmlDocumentCart.builder() //
		    .withItems(MyCollections.requireNonEmpty(forInvoice.getItems(), "forInvoice.getItems"),
			    Item::getName, Item::getQuantity, Item::getTotal) //
		    .build() //
		    .getRawXml());

	    return result;
	}
    }

    @Override
    protected int prime() {
	return PRIME;
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

	return sb.append(sj.toString()) //
		.append(appendEntityId()) //
		.toString();
    }

    // created

    protected Instant created = Instant.now();

    public Instant getCreated() {
	return created;
    }

    // orderNumber

    protected String orderNumber;

    public String getOrderNumber() {
	return orderNumber;
    }

    // amount

    protected Double amount;

    public Double getAmount() {
	return amount;
    }

    // currency

    protected FinCurrency currency;

    public FinCurrency getCurrency() {
	return currency;
    }

    // forInvoice

    protected Invoice forInvoice;

    public Invoice getForInvoice() {
	return forInvoice;
    }

    // payment

    protected QazkomPayment payment;

    public QazkomPayment getPayment() {
	return payment;
    }

    // orderDoc

    protected QazkomXmlDocument orderDoc;

    public QazkomXmlDocument getOrderDoc() {
	return orderDoc;
    }

    // cartDoc

    protected QazkomXmlDocument cartDoc;

    public QazkomXmlDocument getCartDoc() {
	return cartDoc;
    }

    // controllers

    @Override
    public void unlazy() {
	MyOptionals.of(getCartDoc()).ifPresent(AEntity::unlazy);
	MyOptionals.of(getOrderDoc()).ifPresent(AEntity::unlazy);
	MyOptionals.of(getPayment()).ifPresent(AEntity::unlazy);
	MyOptionals.of(getForInvoice()).ifPresent(AEntity::unlazy);
    }

    public void paidBy(QazkomPayment qp) {
	MyObjects.requireNonNull(qp);
	MyStrings.requireEqualsMsg(orderNumber, qp.orderNumber,
		"Qazkom order number and payment order number are not the same");
	MyNumbers.requireEqualsMsg(getAmount(), qp.getAmount(),
		"Qazkom order amount and payment amount are not the same");

	MyObjects.requireNullMsg(qp.order, "Payment already has order attached");
	MyObjects.requireNullMsg(this.payment, "Order already has payment attached");
	// TODO FEAUTURE : Need to implement more Qazkom validation points

	this.payment = qp;
	qp.order = this;
    }

}
