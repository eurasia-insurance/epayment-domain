package com.lapsa.kkb.core;

import static com.lapsa.kkb.core.DisplayNameElements.*;

import java.util.Locale;
import java.util.StringJoiner;

import com.lapsa.commons.function.MyOptionals;

@Deprecated
public class KKBPaymentResponseDocument extends KKBDocument {
    private static final long serialVersionUID = -3421596583719107336L;
    private static final int PRIME = 13;
    private static final int MULTIPLIER = 13;

    @Override
    protected int getPrime() {
	return PRIME;
    }

    @Override
    protected int getMultiplier() {
	return MULTIPLIER;
    }

    @Override
    public String displayName(DisplayNameVariant variant, Locale locale) {
	StringBuilder sb = new StringBuilder();

	sb.append(PAYMENT_RESPONSE_DOCUMENT.displayName(variant, locale));

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
