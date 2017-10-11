package com.lapsa.kkb.core;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.function.Function;

import tech.lapsa.java.commons.function.MyObjects;

public final class DisplayNames {

    public static final Function<LocalDate, String> localDateMapper(Locale locale) {
	return x -> localDateFormatter(locale).format(x);
    }

    public static final Function<LocalDateTime, String> localDateTimeMapper(Locale locale) {
	return x -> localDateTimeFormatter(locale).format(x);
    }

    public static final Function<Instant, String> instantMapper(Locale locale) {
	return x -> instantFormatter(locale).format(x);
    }

    public static final Function<LocalTime, String> localTimeMapper(Locale locale) {
	return x -> localTimeFormatter(locale).format(x);
    }

    public static DateTimeFormatter localDateTimeFormatter(Locale locale) {
	MyObjects.requireNonNull(locale, "locale");
	return new DateTimeFormatterBuilder() //
		.appendLocalized(FormatStyle.MEDIUM, FormatStyle.MEDIUM) //
		.toFormatter(locale);
    }

    public static DateTimeFormatter localDateFormatter(Locale locale) {
	MyObjects.requireNonNull(locale, "locale");
	return new DateTimeFormatterBuilder() //
		.appendLocalized(FormatStyle.MEDIUM, null) //
		.toFormatter(locale);
    }

    public static DateTimeFormatter localTimeFormatter(Locale locale) {
	MyObjects.requireNonNull(locale, "locale");
	return new DateTimeFormatterBuilder() //
		.appendLocalized(null, FormatStyle.MEDIUM) //
		.toFormatter(locale);
    }

    public static DateTimeFormatter instantFormatter(Locale locale) {
	MyObjects.requireNonNull(locale, "locale");
	return new DateTimeFormatterBuilder() //
		.appendInstant() //
		.toFormatter(locale);
    }
}
