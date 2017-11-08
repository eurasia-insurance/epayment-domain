package tech.lapsa.epayment.domain;

import java.security.cert.X509Certificate;
import java.util.Currency;
import java.util.Locale;
import java.util.StringJoiner;

import com.lapsa.fin.FinCurrency;
import com.lapsa.international.phone.PhoneNumber;

import tech.lapsa.epayment.qazkom.xml.bind.XmlBank;
import tech.lapsa.epayment.qazkom.xml.bind.XmlCustomer;
import tech.lapsa.epayment.qazkom.xml.bind.XmlDocumentPayment;
import tech.lapsa.epayment.qazkom.xml.bind.XmlMerchant;
import tech.lapsa.epayment.qazkom.xml.bind.XmlOrder;
import tech.lapsa.epayment.qazkom.xml.bind.XmlPayment;
import tech.lapsa.epayment.qazkom.xml.bind.XmlResults;
import tech.lapsa.java.commons.function.MyCollections;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.java.commons.localization.Localized;
import tech.lapsa.java.commons.security.MySignatures.VerifyingSignature;

public class QazkomPayment extends APayment {

    private static final long serialVersionUID = 1L;
    private static final int PRIME = 7;

    public static QazkomPaymentBuilder builder() {
	return new QazkomPaymentBuilder();
    }

    public static final class QazkomPaymentBuilder {
	private String rawXml;
	private VerifyingSignature signature;
	private X509Certificate certificate;

	private QazkomPaymentBuilder() {
	}

	public QazkomPaymentBuilder fromRawXml(final String rawXml) {
	    this.rawXml = MyStrings.requireNonEmpty(rawXml, "rawXml");
	    return this;
	}

	public QazkomPaymentBuilder withBankSignature(VerifyingSignature signature) {
	    this.signature = MyObjects.requireNonNull(signature, "signature");
	    return this;
	}

	public QazkomPaymentBuilder withBankCertificate(X509Certificate certificate) {
	    this.certificate = MyObjects.requireNonNull(certificate);
	    return this;
	}

	public QazkomPayment build() {
	    MyStrings.requireNonEmpty(rawXml, "rawXml");
	    MyObjects.requireNonNull(signature, "signature");
	    MyObjects.requireNonNull(certificate, "certificate");

	    XmlDocumentPayment document = XmlDocumentPayment.builder() //
		    .ofRawXml(rawXml) //
		    .checkingSignatureWith(signature) //
		    .checkingCertificateWith(certificate) //
		    .build();

	    final XmlResults results = MyOptionals.of(document.getBank()) //
		    .map(XmlBank::getResults) //
		    .orElseThrow(() -> new IllegalArgumentException("Can't parse for payment results"));

	    final XmlPayment payment = MyOptionals.of(results.getPayments()) //
		    .filter(x -> x.size() == 1) // must be exactly one record
		    .flatMap(MyCollections::firstElement) // map to first
							  // element
		    .orElseThrow(() -> new IllegalArgumentException("Can't parse for payment line"));

	    final XmlCustomer customer = MyOptionals.of(document.getBank()) //
		    .map(XmlBank::getCustomer) //
		    .orElseThrow(() -> new IllegalArgumentException("Can't parse for customer"));

	    final QazkomPayment result = new QazkomPayment();

	    result.paymentDoc = new QazkomXmlDocument(rawXml);

	    result.orderNumber = MyOptionals.of(customer) //
		    .map(XmlCustomer::getSourceMerchant) //
		    .map(XmlMerchant::getOrder) //
		    .map(XmlOrder::getOrderId) //
		    .orElseThrow(() -> new IllegalArgumentException("Can't parse for order number"));

	    result.currency = MyOptionals.of(customer) //
		    .map(XmlCustomer::getSourceMerchant) //
		    .map(XmlMerchant::getOrder) //
		    .map(XmlOrder::getCurrency) //
		    .orElseThrow(() -> new IllegalArgumentException("Can't parse for order currency"));

	    result.reference = MyOptionals.of(payment) //
		    .map(XmlPayment::getReference) //
		    .orElseThrow(() -> new IllegalArgumentException("PAYMENT Reference is empty"));

	    result.amount = MyOptionals.of(payment) //
		    .map(XmlPayment::getAmount) //
		    .orElseThrow(() -> new IllegalArgumentException("PAYMENT Amount is empty"));

	    result.created = MyOptionals.of(results) //
		    .map(XmlResults::getTimestamp) //
		    .orElseThrow(() -> new IllegalArgumentException("PAYMENT Timestamp is empty"));

	    result.cardNumber = MyOptionals.of(payment) //
		    .map(XmlPayment::getCardNumberMasked) //
		    .orElse(null);

	    result.payerName = MyOptionals.of(customer) //
		    .map(XmlCustomer::getName) //
		    .orElse(null);

	    result.payerEmail = MyOptionals.of(customer) //
		    .map(XmlCustomer::getEmailAddress) //
		    .orElse(null);

	    result.payerPhoneNumber = MyOptionals.of(customer) //
		    .map(XmlCustomer::getPhone) //
		    .map(PhoneNumber::assertValid)
		    .orElse(null);

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

	sb.append(Localization.QAZKOM_PAYMENT.localized(variant, locale));

	final StringJoiner sj = new StringJoiner(", ", " ", "");
	sj.setEmptyValue("");

	MyOptionals.of(orderNumber) //
		.map(Localization.FIELD_ORDER_NUMBER.fieldAsCaptionMapper(variant, locale)) //
		.ifPresent(sj::add);

	if (amount != null && currency != null) {
	    final FinCurrency c = FinCurrency.byNumericCode(currency.getNumericCode());
	    sj.add(Localization.FIELD_PAYMENT_AMOUNT.fieldAsCaptionMapper(variant, locale)
		    .apply(c.formatAmount(amount)));
	}

	return sb.append(sj.toString()) //
		.append(appendEntityId()) //
		.toString();
    }

    @Override
    public Localized getMethod() {
	return PaymentMethod.QAZKOM;
    }

    // order

    protected QazkomOrder order;

    public QazkomOrder getOrder() {
	return order;
    }

    // orderNumber

    protected String orderNumber;

    public String getOrderNumber() {
	return orderNumber;
    }

    protected Currency currency;

    public Currency getCurrency() {
	return currency;
    }

    // cardNumber

    protected String cardNumber;

    public String getCardNumber() {
	return cardNumber;
    }

    // reference

    protected String reference;

    public String getReference() {
	return reference;
    }

    // payerName

    protected String payerName;

    public String getPayerName() {
	return payerName;
    }

    // payerEmail

    protected String payerEmail;

    public String getPayerEmail() {
	return payerEmail;
    }

    // payerPhoneNumber

    protected PhoneNumber payerPhoneNumber;

    public PhoneNumber getPayerPhoneNumber() {
	return payerPhoneNumber;
    }

    // paymentDoc

    protected QazkomXmlDocument paymentDoc;

    public QazkomXmlDocument getPaymentDoc() {
	return paymentDoc;
    }
}