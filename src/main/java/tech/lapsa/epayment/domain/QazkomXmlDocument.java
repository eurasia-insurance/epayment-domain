package tech.lapsa.epayment.domain;

import java.util.Base64;
import java.util.Locale;
import java.util.Optional;

import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.function.MyStrings;

public class QazkomXmlDocument extends AEntity {

    private static final int PRIME = 19;
    private static final long serialVersionUID = 1L;

    public QazkomXmlDocument() {
    }

    public QazkomXmlDocument(final String rawXml) {
	this.rawXml = MyStrings.requireNonEmpty(rawXml, "rawXml");
    }

    @Override
    protected int prime() {
	return PRIME;
    }

    @Override
    public String localized(final LocalizationVariant variant, final Locale locale) {
	return rawXml;
    }

    // rawXml
    protected String rawXml;

    public String getRawXml() {
	return rawXml;
    }

    public String getBase64Xml() {
	return MyOptionals.of(rawXml) //
		.map(String::getBytes) //
		.map(Base64.getEncoder()::encodeToString) //
		.orElse(null);
    }

    public Optional<String> optionalBase64Xml() {
	return MyOptionals.of(getBase64Xml());
    }

    // controllers
    
    @Override
    public void unlazy() {
    }
}
