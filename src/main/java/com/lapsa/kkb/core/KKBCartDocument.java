package com.lapsa.kkb.core;

import static com.lapsa.kkb.core.DisplayNameElements.*;

import java.util.Locale;
import java.util.StringJoiner;

import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.localization.Localizeds;

@Deprecated
public class KKBCartDocument extends KKBDocument {
    private static final long serialVersionUID = 3602329001760876393L;
    private static final int PRIME = 3;
    private static final int MULTIPLIER = 3;

    @Override
    protected int getPrime() {
	return PRIME;
    }

    @Override
    protected int getMultiplier() {
	return MULTIPLIER;
    }

    @Override
    public String localized(LocalizationVariant variant, Locale locale) {
	StringBuilder sb = new StringBuilder();

	sb.append(CARD_DOCUMENT.localized(variant, locale));

	StringJoiner sj = new StringJoiner(", ", " ", "");
	sj.setEmptyValue("");

	MyOptionals.of(created) //
		.map(Localizeds.instantMapper(locale) //
			.andThen(FIELD_CREATED.fieldAsCaptionMapper(variant, locale))) //
		.ifPresent(sj::add);

	return sb.append(sj.toString()) //
		.append(appendEntityId()) //
		.toString();
    }

    // GENERATED

    public KKBCartDocument() {
	super();
    }

    public KKBCartDocument(String content) {
	super(content);
    }
}
