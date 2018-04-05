package tech.lapsa.epayment.domain;

import java.io.Serializable;
import java.security.cert.X509Certificate;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

@Entity
@Table(name = "QAZKOM_PAYMENT")
@Inheritance(strategy = InheritanceType.JOINED)
@HashCodePrime(7)
public class QazkomPayment extends Payment {

    private static final long serialVersionUID = 2L;

    public static QazkomPaymentBuilder builder() {
	return new QazkomPaymentBuilder();
    }

    public static final class QazkomPaymentBuilder implements Serializable {

	private static final long serialVersionUID = 1L;

	private String rawXml;
	private X509Certificate certificate;
	private boolean signatureCheckRequired = true;

	private QazkomPaymentBuilder() {
	}

	public QazkomPaymentBuilder fromRawXml(final String rawXml) throws IllegalArgumentException {
	    this.rawXml = MyStrings.requireNonEmpty(rawXml, "rawXml");
	    return this;
	}

	public QazkomPaymentBuilder withBankCertificate(final X509Certificate certificate)
		throws IllegalArgumentException {
	    this.certificate = MyObjects.requireNonNull(certificate, "certificate");
	    return this;
	}

	public QazkomPaymentBuilder withOptionalSignatureChecking() {
	    signatureCheckRequired = false;
	    return this;
	}

	public QazkomPayment build() throws IllegalArgumentException {

	    MyStrings.requireNonEmpty(rawXml, "rawXml");

	    final XmlDocumentPaymentBuilder documentBuilder = XmlDocumentPayment.builder() //
		    .ofRawXml(rawXml);

	    if (signatureCheckRequired)
		MyObjects.requireNonNullMsg(certificate, "Bank certificate must be provided for signature checking");

	    if (MyObjects.nonNull(certificate))
		documentBuilder.withBankCertificate(certificate);

	    final XmlDocumentPayment document = documentBuilder.build();

	    final XmlResults results = MyOptionals.of(document.getBank()) //
		    .map(XmlBank::getResults) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplier("Can't parse for payment results"));

	    final XmlPayment payment = MyOptionals.of(results.getPayments()) //
		    .filter(x -> x.size() == 1) // must be exactly one record
		    .flatMap(MyCollections::firstElement) // map to first
							  // element
		    .orElseThrow(MyExceptions.illegalArgumentSupplier("Can't parse for payment line"));

	    final XmlCustomer customer = MyOptionals.of(document.getBank()) //
		    .map(XmlBank::getCustomer) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplier("Can't parse for customer"));

	    final QazkomPayment result = new QazkomPayment();

	    result.paymentDoc = new QazkomXmlDocument(rawXml, QazkomXmlDocument.DocumentType.PAYMENT);

	    result.orderNumber = MyOptionals.of(customer) //
		    .map(XmlCustomer::getSourceMerchant) //
		    .map(XmlMerchant::getOrder) //
		    .map(XmlOrder::getOrderId) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplier("Can't parse for order number"));

	    result.currency = MyOptionals.of(customer) //
		    .map(XmlCustomer::getSourceMerchant) //
		    .map(XmlMerchant::getOrder) //
		    .map(XmlOrder::getCurrency) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplier("Can't parse for order currency"));

	    result.reference = MyOptionals.of(payment) //
		    .map(XmlPayment::getReference) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplier("Payment reference is empty"));

	    result.amount = MyOptionals.of(payment) //
		    .map(XmlPayment::getAmount) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplier("Payment amount is empty"));

	    result.created = MyOptionals.of(results) //
		    .map(XmlResults::getTimestamp) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplier("Payment timestamp is empty"));

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
	    final NumberFormat nf = NumberFormat.getCurrencyInstance();
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

    // constructor

    protected QazkomPayment() {
    }

    // order

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "payment")
    protected QazkomOrder order;

    public QazkomOrder getOrder() {
	return order;
    }

    public Optional<QazkomOrder> optionalOrder() {
	return MyOptionals.of(getOrder());
    }

    // orderNumber

    @Basic
    @Column(name = "ORDER_NUMBER", unique = true)
    protected String orderNumber;

    public String getOrderNumber() {
	return orderNumber;
    }

    // cardNumber

    @Basic
    @Column(name = "CARD_NUMBER")
    protected String cardNumber;

    public String getCardNumber() {
	return cardNumber;
    }

    // payerEmail

    @Basic
    @Column(name = "PAYER_EMAIL")
    protected String payerEmail;

    public String getPayerEmail() {
	return payerEmail;
    }

    // payerPhoneNumber

    @Basic
    @Column(name = "PAYER_PHONE_NUMBER")
    protected PhoneNumber payerPhoneNumber;

    public PhoneNumber getPayerPhoneNumber() {
	return payerPhoneNumber;
    }

    // paymentDoc

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PAYMENT_DOC_ID")
    protected QazkomXmlDocument paymentDoc;

    public QazkomXmlDocument getPaymentDoc() {
	return paymentDoc;
    }

    // controllers

    @Override
    public void unlazy() {
	super.unlazy();
	MyOptionals.of(getPaymentDoc()).ifPresent(IntIdEntitySuperclass::unlazy);
    }
}
