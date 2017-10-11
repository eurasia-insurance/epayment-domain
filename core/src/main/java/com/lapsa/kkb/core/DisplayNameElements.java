package com.lapsa.kkb.core;

import java.util.Locale;
import java.util.function.Function;

import tech.lapsa.java.commons.localization.LocalizedElement;

public enum DisplayNameElements implements LocalizedElement {
    CARD_DOCUMENT,
    //
    ORDER,
    //
    ORDER_ITEM,
    ORDER_ITEM_NAME,
    ORDER_ITEM_QUANTITY,
    //
    PAYMENT_ERROR_DOCUMENT,
    //
    PAYMENT_REQUEST_DOCUMENT,
    //
    PAYMENT_RESPONSE_DOCUMENT,
    //
    PAYMENT_SIGNED_DATA,
    //
    FIELD_CREATED,
    FIELD_STATUS,
    ;

    public Function<String, String> fieldAsCaptionMapper(final DisplayNameVariant variant,
	    final Locale locale) {
	return x -> displayName(variant, locale) + " '" + x + "'";
    }
}
