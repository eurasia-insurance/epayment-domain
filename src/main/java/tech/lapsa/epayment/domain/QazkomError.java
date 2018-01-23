package tech.lapsa.epayment.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tech.lapsa.epayment.qazkom.xml.bind.XmlDocumentError;
import tech.lapsa.epayment.qazkom.xml.bind.XmlError;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.java.commons.localization.Localizeds;
import tech.lapsa.patterns.domain.HashCodePrime;

@Entity
@Table(name = "QAZKOM_ERROR")
@HashCodePrime(29)
public class QazkomError extends IntIdEntitySuperclass {

    private static final long serialVersionUID = 1L;

    public static QazkomErrorBuilder builder() {
	return new QazkomErrorBuilder();
    }

    public static final class QazkomErrorBuilder implements Serializable {

	private static final long serialVersionUID = 1L;

	private String rawXml;

	private QazkomErrorBuilder() {
	}

	public QazkomErrorBuilder fromRawXml(final String rawXml) throws IllegalArgumentException {
	    this.rawXml = MyStrings.requireNonEmpty(rawXml, "rawXml");
	    return this;
	}

	public QazkomError build() throws IllegalArgumentException {

	    MyStrings.requireNonEmpty(rawXml, "rawXml");

	    final XmlDocumentError document = XmlDocumentError.of(rawXml);

	    final QazkomError result = new QazkomError();

	    result.errorDoc = new QazkomXmlDocument(rawXml, QazkomXmlDocument.DocumentType.ERROR);

	    result.orderNumber = MyOptionals.of(document) //
		    .map(XmlDocumentError::getOrderId) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplier("Can't parse for order number"));

	    result.created = MyOptionals.of(document) //
		    .map(XmlDocumentError::getError) //
		    .map(XmlError::getTime) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplier("Payment timestamp is empty"));

	    result.message = MyOptionals.of(document) //
		    .map(XmlDocumentError::getError) //
		    .map(XmlError::getMessage) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplier("Message is empty"));

	    result.code = MyOptionals.of(document) //
		    .map(XmlDocumentError::getError) //
		    .map(XmlError::getCode) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplier("Message is empty"));

	    return result;
	}
    }

    // constructor

    protected QazkomError() {
    }

    // created

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    protected Instant created = Instant.now();

    public Instant getCreated() {
	return created;
    }

    // order

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "QAZKOM_ORDER_ID")
    protected QazkomOrder order;

    public QazkomOrder getOrder() {
	return order;
    }

    public Optional<QazkomOrder> optionalOrder() {
	return MyOptionals.of(getOrder());
    }

    // orderNumber

    @Basic
    @Column(name = "ORDER_NUMBER")
    protected String orderNumber;

    public String getOrderNumber() {
	return orderNumber;
    }

    // paymentDoc

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ERROR_DOC_ID")
    protected QazkomXmlDocument errorDoc;

    public QazkomXmlDocument getPaymentDoc() {
	return errorDoc;
    }

    // message

    @Basic
    @Column(name = "MESSAGE")
    protected String message;

    public String getMessage() {
	return message;
    }

    // code

    @Basic
    @Column(name = "CODE")
    protected String code;

    public String getCode() {
	return code;
    }

    // CONTROLLERS

    @Override
    public String localized(final LocalizationVariant variant, final Locale locale) {
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
