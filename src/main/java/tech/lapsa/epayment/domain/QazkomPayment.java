package tech.lapsa.epayment.domain;

import java.security.cert.X509Certificate;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;

import com.lapsa.international.phone.PhoneNumber;

import tech.lapsa.epayment.qazkom.xml.bind.XmlBank;
import tech.lapsa.epayment.qazkom.xml.bind.XmlCustomer;
import tech.lapsa.epayment.qazkom.xml.bind.XmlDocumentPayment;
import tech.lapsa.epayment.qazkom.xml.bind.XmlDocumentPayment.XmlDocumentPaymentBuilder;
import tech.lapsa.epayment.qazkom.xml.bind.XmlMerchant;
import tech.lapsa.epayment.qazkom.xml.bind.XmlOrder;
import tech.lapsa.epayment.qazkom.xml.bind.XmlPayment;
import tech.lapsa.epayment.qazkom.xml.bind.XmlResults;
import tech.lapsa.java.commons.function.MyCollections;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.java.commons.localization.Localizeds;
import tech.lapsa.patterns.domain.HashCodePrime;

@HashCodePrime(7)
public class QazkomPayment extends Payment {

    private static final long serialVersionUID = 1L;

    public static QazkomPaymentBuilder builder() {
	return new QazkomPaymentBuilder();
    }

    public static final class QazkomPaymentBuilder {
	private String rawXml;
	private X509Certificate certificate;
	private boolean signatureCheckRequired = true;

	private QazkomPaymentBuilder() {
	}

	public QazkomPaymentBuilder fromRawXml(final String rawXml) {
	    this.rawXml = MyStrings.requireNonEmpty(rawXml, "rawXml");
	    return this;
	}

	public QazkomPaymentBuilder withBankCertificate(X509Certificate certificate) {
	    this.certificate = MyObjects.requireNonNull(certificate, "certificate");
	    return this;
	}

	public QazkomPaymentBuilder withOptionalSignatureChecking() {
	    this.signatureCheckRequired = false;
	    return this;
	}

	public QazkomPayment build() {

	    MyStrings.requireNonEmpty(rawXml, "rawXml");

	    XmlDocumentPaymentBuilder documentBuilder = XmlDocumentPayment.builder() //
		    .ofRawXml(rawXml);

	    if (signatureCheckRequired)
		MyObjects.requireNonNullMsg(certificate, "Bank certificate must be provided for signature checking");

	    if (MyObjects.nonNull(certificate))
		documentBuilder.withBankCertificate(certificate);

	    XmlDocumentPayment document = documentBuilder.build();

	    final XmlResults results = MyOptionals.of(document.getBank()) //
		    .map(XmlBank::getResults) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplierFormat("Can't parse for payment results"));

	    final XmlPayment payment = MyOptionals.of(results.getPayments()) //
		    .filter(x -> x.size() == 1) // must be exactly one record
		    .flatMap(MyCollections::firstElement) // map to first
							  // element
		    .orElseThrow(MyExceptions.illegalArgumentSupplierFormat("Can't parse for payment line"));

	    final XmlCustomer customer = MyOptionals.of(document.getBank()) //
		    .map(XmlBank::getCustomer) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplierFormat("Can't parse for customer"));

	    final QazkomPayment result = new QazkomPayment();

	    result.paymentDoc = new QazkomXmlDocument(rawXml, QazkomXmlDocument.DocumentType.PAYMENT);

	    result.orderNumber = MyOptionals.of(customer) //
		    .map(XmlCustomer::getSourceMerchant) //
		    .map(XmlMerchant::getOrder) //
		    .map(XmlOrder::getOrderId) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplierFormat("Can't parse for order number"));

	    result.currency = MyOptionals.of(customer) //
		    .map(XmlCustomer::getSourceMerchant) //
		    .map(XmlMerchant::getOrder) //
		    .map(XmlOrder::getCurrency) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplierFormat("Can't parse for order currency"));

	    result.referenceNumber = MyOptionals.of(payment) //
		    .map(XmlPayment::getReference) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplierFormat("Payment reference is empty"));

	    result.amount = MyOptionals.of(payment) //
		    .map(XmlPayment::getAmount) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplierFormat("Payment amount is empty"));

	    result.created = MyOptionals.of(results) //
		    .map(XmlResults::getTimestamp) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplierFormat("Payment timestamp is empty"));

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
    public String localized(final LocalizationVariant variant, final Locale locale) {
	final StringBuilder sb = new StringBuilder();

	sb.append(Localization.QAZKOMPAYMENT_NAME.localized(variant, locale));

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
	    NumberFormat nf = NumberFormat.getCurrencyInstance();
	    nf.setCurrency(currency);
	    sj.add(Localization.PAYMENT_FIELD_AMOUNT.fieldAsCaptionMapper(variant, locale)
		    .apply(nf.format(amount)));
	}

	return sb.append(sj.toString()) //
		.append(appendEntityId()) //
		.toString();
    }

    @Override
    public PaymentMethod getMethod() {
	return PaymentMethod.QAZKOM;
    }

    // order

    protected QazkomOrder order;

    public QazkomOrder getOrder() {
	return order;
    }

    public Optional<QazkomOrder> optionalOrder() {
	return MyOptionals.of(getOrder());
    }

    // orderNumber

    protected String orderNumber;

    public String getOrderNumber() {
	return orderNumber;
    }

    // cardNumber

    protected String cardNumber;

    public String getCardNumber() {
	return cardNumber;
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

    // controllers

    @Override
    public void unlazy() {
	super.unlazy();
	MyOptionals.of(getPaymentDoc()).ifPresent(BaseEntity::unlazy);
    }
}
