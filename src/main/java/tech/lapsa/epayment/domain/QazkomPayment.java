package tech.lapsa.epayment.domain;

import java.util.Base64;
import java.util.Currency;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;

import com.lapsa.fin.FinCurrency;
import com.lapsa.international.phone.PhoneNumber;

import tech.lapsa.java.commons.function.MyCollections;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.java.commons.localization.Localized;
import tech.lapsa.qazkom.xml.XmlDocuments;
import tech.lapsa.qazkom.xml.mapping.XmlBank;
import tech.lapsa.qazkom.xml.mapping.XmlCustomer;
import tech.lapsa.qazkom.xml.mapping.XmlDocumentPayment;
import tech.lapsa.qazkom.xml.mapping.XmlMerchant;
import tech.lapsa.qazkom.xml.mapping.XmlOrder;
import tech.lapsa.qazkom.xml.mapping.XmlPayment;
import tech.lapsa.qazkom.xml.mapping.XmlResults;

public class QazkomPayment extends APayment {

    private static final long serialVersionUID = 1L;

    public static QazkomPayment from(String rawXml) {
	MyStrings.requireNonEmpty(rawXml, "rawXml");

	XmlDocumentPayment document = XmlDocuments.PAYMENT.parse(rawXml);

	XmlResults results = MyOptionals.of(document.getBank()) //
		.map(XmlBank::getResults) //
		.orElseThrow(() -> new IllegalArgumentException("Can't parse for payment results"));

	XmlPayment payment = MyOptionals.of(results.getPayments()) //
		.filter(x -> x.size() == 1) // must be exactly one record
		.flatMap(MyCollections::firstElement) // map to first element
		.orElseThrow(() -> new IllegalArgumentException("Can't parse for payment line"));

	XmlCustomer customer = MyOptionals.of(document.getBank()) //
		.map(XmlBank::getCustomer) //
		.orElseThrow(() -> new IllegalArgumentException("Can't parse for customer"));

	QazkomPayment result = new QazkomPayment();

	result.rawXml = rawXml;

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
		.map(PhoneNumber::parse)
		.orElse(null);

	return result;
    }

    public QazkomPayment() {
	super(7);
    }

    @Override
    public String localized(LocalizationVariant variant, Locale locale) {
	StringBuilder sb = new StringBuilder();

	sb.append(Localization.QAZKOM_PAYMENT.localized(variant, locale));

	StringJoiner sj = new StringJoiner(", ", " ", "");
	sj.setEmptyValue("");

	MyOptionals.of(orderNumber) //
		.map(Localization.FIELD_ORDER_NUMBER.fieldAsCaptionMapper(variant, locale)) //
		.ifPresent(sj::add);

	if (amount != null && currency != null) {
	    FinCurrency c = FinCurrency.byNumericCode(currency.getNumericCode());
	    sj.add(Localization.FIELD_PAYMENT_AMOUNT.fieldAsCaptionMapper(variant, locale)
		    .apply(c.formatAmount(amount)));
	}

	return sb.append(sj.toString()) //
		.append(appendEntityId()) //
		.toString();
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

    private Currency currency;

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

    public Optional<XmlDocumentPayment> optionalDocument() {
	return MyOptionals.of(rawXml) //
		.flatMap(XmlDocuments.PAYMENT::optionalParse);
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

    @Override
    public Localized getMethod() {
	return PaymentMethod.QAZKOM;
    }
}
