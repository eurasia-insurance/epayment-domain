package tech.lapsa.epayment.domain;

import java.time.Instant;
import java.util.Base64;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;

import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.localization.Localizeds;
import tech.lapsa.qazkom.xml.XmlDocuments;
import tech.lapsa.qazkom.xml.mapping.XmlDocumentOrder;

public class QazkomOrder extends AEntity<Integer> {

    private static final long serialVersionUID = 1L;

    public static Long orderNumberAsLong() {
	UUID uuid = UUID.randomUUID();
	return Math.abs(uuid.getLeastSignificantBits() / 10000);
    }

    public static String orderNumberAsString() {
	return String.valueOf(orderNumberAsLong());
    }

    @Override
    public String localized(LocalizationVariant variant, Locale locale) {
	StringBuilder sb = new StringBuilder();

	sb.append(Localization.QAZKOM_ORDER.localized(variant, locale));

	StringJoiner sj = new StringJoiner(", ", " ", "");
	sj.setEmptyValue("");

	MyOptionals.of(orderNumber) //
		.map(Localization.FIELD_ORDER_NUMBER.fieldAsCaptionMapper(variant, locale)) //
		.ifPresent(sj::add);

	MyOptionals.of(created) //
		.map(Localizeds.instantMapper(locale)) //
		.map(Localization.FIELD_CREATED.fieldAsCaptionMapper(variant, locale)) //
		.ifPresent(sj::add);

	return sb.append(sj.toString()) //
		.append(appendEntityId()) //
		.toString();
    }

    // created

    protected Instant created = Instant.now();

    public Instant getCreated() {
	return created;
    }

    // orderNumber

    protected String orderNumber;

    public String getOrderNumber() {
	return orderNumber;
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
