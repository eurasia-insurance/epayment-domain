package tech.lapsa.epayment.domain;

import java.util.Base64;
import java.util.Locale;
import java.util.Optional;

import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.function.MyStrings;

public class QazkomXmlDocument extends Entity {

    private static final int PRIME = 19;
    private static final long serialVersionUID = 1L;

    public static enum DocumentType {
	ORDER, PAYMENT, CART;
    }

    public QazkomXmlDocument() {
    }

    public QazkomXmlDocument(final String rawXml, final DocumentType type) {
	this.rawXml = MyStrings.requireNonEmpty(rawXml, "rawXml");
	this.type = MyObjects.requireNonNull(type, "type");
    }

    @Override
    protected int prime() {
	return PRIME;
    }

    @Override
    public String localized(final LocalizationVariant variant, final Locale locale) {
	return MyOptionals.of(rawXml) //
		.orElseGet(() -> Localization.QAZKOMXMLDOC_EMPTYNAME.localized(variant, locale));
    }

    // rawXml
    protected String rawXml;

    public String getRawXml() {
	return rawXml;
    }

    public Optional<String> optionalRawXml() {
	return MyOptionals.of(getRawXml());
    }

    public String getBase64Xml() {
	return optionalRawXml() //
		.map(String::getBytes) //
		.map(Base64.getEncoder()::encodeToString) //
		.orElse(null);
    }

    public Optional<String> optionalBase64Xml() {
	return MyOptionals.of(getBase64Xml());
    }

    // type

    protected DocumentType type;

    public DocumentType getType() {
	return type;
    }

    public boolean isOrder() {
	return type == DocumentType.ORDER;
    }

    public boolean isCart() {
	return type == DocumentType.CART;
    }

    public boolean isPayment() {
	return type == DocumentType.PAYMENT;
    }

    // controllers

    @Override
    public void unlazy() {
    }
}
