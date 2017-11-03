package tech.lapsa.epayment.domain;

import java.util.Locale;
import java.util.function.Function;

import tech.lapsa.java.commons.localization.LocalizedElement;

public enum Localization implements LocalizedElement {
    QAZKOM_ORDER, //
    QAZKOM_PAYMENT, //
    ITEM_EMPTY,
    FIELD_ITEM_PRICE,
    FIELD_ITEM_QUANTITY,
    FIELD_ITEM_TOTAL,
    FIELD_ORDER_NUMBER, //
    FIELD_CREATED, //
    FIELD_PAYMENT_AMOUNT,
    ;

    public Function<String, String> fieldAsCaptionMapper(final LocalizationVariant variant,
	    final Locale locale) {
	return x -> localized(variant, locale) + " '" + x + "'";
    }
}
