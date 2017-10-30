package tech.lapsa.epayment.domain;

import java.util.Base64;
import java.util.Locale;
import java.util.Optional;

import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.localization.Localized;
import tech.lapsa.qazkom.xml.XmlDocuments;
import tech.lapsa.qazkom.xml.mapping.XmlDocumentPayment;

public class QazkomPayment extends APayment {

    private static final long serialVersionUID = 1L;

    public QazkomPayment() {
	super(7);
    }

    @Override
    public String localized(LocalizationVariant variant, Locale locale) {
	// TODO Auto-generated method stub
	return QazkomPayment.class.getName();
    }

    // order

    protected QazkomOrder order;

    public QazkomOrder getOrder() {
	return order;
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

    @Override
    public Localized getMethod() {
	return PaymentMethod.QAZKOM;
    }
}
