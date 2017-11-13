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

    public static String generateNumber(final Predicate<String> numberIsUnique)
	    throws NumberOfAttemptsExceedException {
	MyObjects.requireNonNull(numberIsUnique, "numberIsUnique");
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
	} while (!numberIsUnique.test(number));
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
	private Predicate<String> numberIsUnique;

	private QazkomOrderBuilder() {
	}

	public QazkomOrderBuilder forInvoice(final Invoice forInvoice) {
	    this.forInvoice = MyObjects.requireNonNull(forInvoice, "forInvoice");
	    return this;
	}

	public QazkomOrderBuilder withNumber(final String orderNumber) {
	    this.orderNumber = MyStrings.requireNonEmpty(orderNumber, "orderNumber");
	    this.numberIsUnique = null;
	    return this;
	}

	public QazkomOrderBuilder withNumber(final String orderNumber, final Predicate<String> numberIsUnique) {
	    this.orderNumber = MyStrings.requireNonEmpty(orderNumber, "orderNumber");
	    this.numberIsUnique = MyObjects.requireNonNull(numberIsUnique, "numberIsUnique");
	    return this;
	}

	public QazkomOrderBuilder withGeneratedNumber(final Predicate<String> numberIsUnique) {
	    this.orderNumber = null;
	    this.numberIsUnique = MyObjects.requireNonNull(numberIsUnique, "numberIsUnique");
	    return this;
	}
	
	public QazkomOrderBuilder withGeneratedNumber() {
	    this.orderNumber = null;
	    this.numberIsUnique = null;
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
		result.orderNumber = MyObjects.nonNull(numberIsUnique) //
			? Invoice.generateNumber(numberIsUnique) //
			: Invoice.generateNumber();
	    } else {
		// using user value
		if (MyObjects.nonNull(numberIsUnique) && !numberIsUnique.test(orderNumber))
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

	sb.append(Localization.QAZKOM_ORDER.localized(variant, locale));

	final StringJoiner sj = new StringJoiner(", ", " ", "");
	sj.setEmptyValue("");

	MyOptionals.of(orderNumber) //
		.map(Localization.FIELD_ORDER_NUMBER.fieldAsCaptionMapper(variant, locale)) //
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

}
