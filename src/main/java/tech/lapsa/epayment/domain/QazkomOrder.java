package tech.lapsa.epayment.domain;

import java.time.Instant;
import java.util.Base64;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.qazkom.xml.XmlDocuments;
import tech.lapsa.qazkom.xml.mapping.XmlDocumentOrder;

public class QazkomOrder extends AEntity<Long> {

    private static final long serialVersionUID = 1L;

    public static Long idAsLong() {
	UUID uuid = UUID.randomUUID();
	return Math.abs(uuid.getLeastSignificantBits() / 10000);
    }

    public static String idAsString() {
	return String.valueOf(idAsLong());
    }

    public QazkomOrder() {
	super(idAsLong(), 11);
    }

    @Override
    public String localized(LocalizationVariant variant, Locale locale) {
	// TODO Auto-generated method stub
	return QazkomOrder.class.getName();
    }

    // created

    protected Instant created = Instant.now();

    public Instant getCreated() {
	return created;
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
		.flatMap(XmlDocuments.ORDER::optionalParse);
    }

}
