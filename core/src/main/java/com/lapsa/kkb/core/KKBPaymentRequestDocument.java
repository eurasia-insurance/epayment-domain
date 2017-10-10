package com.lapsa.kkb.core;

import static com.lapsa.kkb.core.DisplayNameElements.*;

import java.util.Locale;
import java.util.StringJoiner;

import com.lapsa.commons.function.MyOptionals;

@Deprecated
public class KKBPaymentRequestDocument extends KKBDocument {
    private static final long serialVersionUID = -4716980468384620898L;
    private static final int PRIME = 11;
    private static final int MULTIPLIER = 11;

    @Override
    protected int getPrime() {
	return PRIME;
    }

    @Override
    protected int getMultiplier() {
	return MULTIPLIER;
    }

    // GENERATED

    public KKBPaymentRequestDocument() {
	super();
    }

    public KKBPaymentRequestDocument(String content) {
	super(content);
    }

    @Override
    public String displayName(DisplayNameVariant variant, Locale locale) {
	StringBuilder sb = new StringBuilder();

	sb.append(PAYMENT_REQUEST_DOCUMENT.displayName(variant, locale));

	StringJoiner sj = new StringJoiner(", ", " ", "");
	sj.setEmptyValue("");

	MyOptionals.of(created) //
		.map(DisplayNames.instantMapper(locale) //
			.andThen(FIELD_CREATED.fieldAsCaptionMapper(variant, locale))) //
		.ifPresent(sj::add);

	return sb.append(sj.toString()) //
		.append(appendEntityId()) //
		.toString();
    }
}