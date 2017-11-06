package tech.lapsa.epayment.domain;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.Base64;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;

import com.lapsa.fin.FinCurrency;

import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.java.commons.localization.Localizeds;
import tech.lapsa.qazkom.xml.mapping.XmlDocumentOrder;

public class QazkomOrder extends AEntity {

    private static final long serialVersionUID = 1L;
    private static final int PRIME = 11;

    public static Long orderNumberAsLong() {
	UUID uuid = UUID.randomUUID();
	return Math.abs(uuid.getLeastSignificantBits() / 10000);
    }

    public static String orderNumberAsString() {
	return String.valueOf(orderNumberAsLong());
    }

    public static QazkomOrderBuilder builder() {
	return new QazkomOrderBuilder();
    }

    public static class QazkomOrderBuilder {
	private String orderNumber;
	private Invoice forInvoice;

	private String merchantId;
	private String merchantName;
	private Signature merchantSignature;
	private X509Certificate merchantCertificate;

	private QazkomOrderBuilder() {
	}

	public QazkomOrderBuilder forInvoice(final Invoice forInvoice) {
	    this.forInvoice = MyObjects.requireNonNull(forInvoice, "forInvoice");
	    return this;
	}

	public QazkomOrderBuilder withMerchant(final String merchantId, final String merchantName,
		final X509Certificate merchantCertificate,
		final PrivateKey merchantKey, final String signatureAlgorithm)
		throws NoSuchAlgorithmException, InvalidKeyException {
	    MyObjects.requireNonNull(merchantKey, "merchantKey");
	    MyStrings.requireNonEmpty(signatureAlgorithm, "signatureAlgorithm");
	    this.merchantSignature = Signature.getInstance(signatureAlgorithm);
	    merchantSignature.initSign(merchantKey);
	    return withMerchant(merchantId, merchantName, merchantCertificate, merchantSignature);
	}

	public QazkomOrderBuilder withMerchant(final String merchantId, final String merchantName,
		final X509Certificate merchantCertificate,
		final Signature merchantSignature) {
	    this.merchantId = MyStrings.requireNonEmpty(merchantId, "merchantId");
	    this.merchantName = MyStrings.requireNonEmpty(merchantName, "merchantName");
	    this.merchantSignature = MyObjects.requireNonNull(merchantSignature, "merchantSignature");
	    this.merchantCertificate = MyObjects.requireNonNull(merchantCertificate, "merchantCertificate");
	    return this;
	}

	public QazkomOrder buildAndSign() {
	    QazkomOrder result = new QazkomOrder();

	    result.orderNumber = MyOptionals.of(orderNumber) //
		    .orElseGet(QazkomOrder::orderNumberAsString);

	    result.forInvoice = MyObjects.requireNonNull(forInvoice, "forInvoice");
	    result.amount = forInvoice.getAmount();
	    result.currency = forInvoice.currency;

	    XmlDocumentOrder doc = XmlDocumentOrder.builder() //
		    .withOrderNumber(result.orderNumber) //
		    .withAmount(result.amount) //
		    .withCurrency(result.currency) //
		    .withMerchchant(merchantId, merchantName) //
		    .signWith(merchantSignature, merchantCertificate) //
		    .build();

	    result.rawXml = doc.getRawXml();

	    return result;
	}
    }

    @Override
    protected int prime() {
	return PRIME;
    }

    @Override
    public String localized(LocalizationVariant variant, Locale locale) {
	StringBuilder sb = new StringBuilder();

	sb.append(Localization.QAZKOM_ORDER.localized(variant, locale));

	StringJoiner sj = new StringJoiner(", ", " ", "");
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

    // rawXml

    protected String rawXml;

    public String getRawXml() {
	return rawXml;
    }

    public Optional<String> optionalBase64Xml() {
	return MyOptionals.of(rawXml) //
		.map(String::getBytes) //
		.map(Base64.getEncoder()::encodeToString);
    }

    public Optional<XmlDocumentOrder> optionalDocument() {
	return MyOptionals.of(rawXml) //
		.map(XmlDocumentOrder::of);
	
    }

}
