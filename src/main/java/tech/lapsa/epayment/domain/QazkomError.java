package tech.lapsa.epayment.domain;

import java.time.Instant;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;

import tech.lapsa.epayment.qazkom.xml.bind.XmlDocumentError;
import tech.lapsa.epayment.qazkom.xml.bind.XmlError;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.java.commons.localization.Localizeds;
import tech.lapsa.patterns.domain.HashCodePrime;

@HashCodePrime(29)
public class QazkomError extends Entity {

    private static final long serialVersionUID = 1L;

    public static QazkomErrorBuilder builder() {
	return new QazkomErrorBuilder();
    }

    public static final class QazkomErrorBuilder {
	private String rawXml;

	private QazkomErrorBuilder() {
	}

	public QazkomErrorBuilder fromRawXml(final String rawXml) {
	    this.rawXml = MyStrings.requireNonEmpty(rawXml, "rawXml");
	    return this;
	}

	public QazkomError build() {

	    MyStrings.requireNonEmpty(rawXml, "rawXml");

	    final XmlDocumentError document = XmlDocumentError.of(rawXml);

	    final QazkomError result = new QazkomError();

	    result.errorDoc = new QazkomXmlDocument(rawXml, QazkomXmlDocument.DocumentType.ERROR);

	    result.orderNumber = MyOptionals.of(document) //
		    .map(XmlDocumentError::getOrderId) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplierFormat("Can't parse for order number"));

	    result.created = MyOptionals.of(document) //
		    .map(XmlDocumentError::getError) //
		    .map(XmlError::getTime) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplierFormat("Payment timestamp is empty"));

	    return result;
	}
    }

    // created

    protected Instant created = Instant.now();

    public Instant getCreated() {
	return created;
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

    // paymentDoc

    protected QazkomXmlDocument errorDoc;

    public QazkomXmlDocument getPaymentDoc() {
	return errorDoc;
    }

    @Override
    public String localized(LocalizationVariant variant, Locale locale) {
	final StringBuilder sb = new StringBuilder();

	sb.append(Localization.QAZKOMERROR_NAME.localized(variant, locale));

	final StringJoiner sj = new StringJoiner(", ", " ", "");
	sj.setEmptyValue("");

	MyOptionals.of(created) //
		.map(Localizeds.instantMapper(locale)) //
		.map(Localization.FIELD_CREATED.fieldAsCaptionMapper(variant, locale)) //
		.ifPresent(sj::add);

	return sb.append(sj.toString()) //
		.append(appendEntityId()) //
		.toString();
    }
}
