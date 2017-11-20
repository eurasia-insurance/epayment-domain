package tech.lapsa.epayment.domain;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.StringJoiner;

import tech.lapsa.java.commons.function.MyNumbers;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.localization.Localizeds;

public class UnknownPayment extends Payment {

    private static final long serialVersionUID = 1L;
    private static final int PRIME = 23;

    public static UnknownPaymentBuilder builder() {
	return new UnknownPaymentBuilder();
    }

    public static final class UnknownPaymentBuilder {
	private Currency currency;
	private Double amount;

	private UnknownPaymentBuilder() {
	}

	public UnknownPaymentBuilder withAmount(final Double amount) {
	    this.amount = MyNumbers.requirePositive(amount, "amount");
	    return this;
	}

	public UnknownPaymentBuilder withCurrency(final Currency currency) {
	    this.currency = MyObjects.requireNonNull(currency, "currency");
	    return this;
	}

	public UnknownPaymentBuilder withAmountAndCurrency(final Double amount, final Currency currency) {
	    withAmount(amount);
	    withCurrency(currency);
	    return this;
	}

	public UnknownPayment build() {
	    final UnknownPayment result = new UnknownPayment();
	    result.currency = MyObjects.requireNonNull(currency, "currency");
	    result.amount = MyNumbers.requirePositive(amount, "amount");

	    return result;
	}
    }

    @Override
    protected int prime() {
	return PRIME;
    }

    @Override
    public String localized(LocalizationVariant variant, Locale locale) {
	final StringBuilder sb = new StringBuilder();

	sb.append(Localization.UNKNOWNPAYMENT_NAME.localized(variant, locale));

	final StringJoiner sj = new StringJoiner(", ", " ", "");
	sj.setEmptyValue("");

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
	return PaymentMethod.UNKNOWN;
    }
}
