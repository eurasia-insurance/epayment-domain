package tech.lapsa.epayment.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.lapsa.international.localization.LocalizationLanguage;
import com.lapsa.international.phone.PhoneNumber;

import tech.lapsa.epayment.domain.Exceptions.IsNotExpiredException;
import tech.lapsa.epayment.domain.Exceptions.IsNotPaidException;
import tech.lapsa.epayment.domain.Exceptions.IsNotPendingException;
import tech.lapsa.epayment.domain.Exceptions.IsPaidException;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyNumbers;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.java.commons.localization.Localized;
import tech.lapsa.java.commons.localization.Localizeds;
import tech.lapsa.kz.taxpayer.TaxpayerNumber;
import tech.lapsa.patterns.domain.HashCodePrime;

@HashCodePrime(3)
public class Invoice extends Entity {

    private static final long serialVersionUID = 1L;

    public static String generateNumber() {
	return UUID.randomUUID() //
		.toString() //
	;
    }

    public static String generateNumber(final Predicate<String> numberIsUniqueTest)
	    throws NumberOfAttemptsExceedException {
	MyObjects.requireNonNull(numberIsUniqueTest, "numberIsUniqueTest");
	final int NUMBER_OF_ATTEMPTS = 100;
	int attempt = 0;
	String number;
	do {
	    number = generateNumber();
	    if (attempt++ > NUMBER_OF_ATTEMPTS)
		throw new NumberOfAttemptsExceedException(
			String.format(
				"The number of attempts is exceed the limit (%1$d) while generating the unique number",
				NUMBER_OF_ATTEMPTS));
	} while (!numberIsUniqueTest.test(number));
	return number;
    }

    public static InvoiceBuilder builder() {
	return new InvoiceBuilder();
    }

    public static final class InvoiceBuilder implements Serializable {

	private static final long serialVersionUID = 1L;

	private class Itm implements Serializable {

	    private static final long serialVersionUID = 1L;

	    private final String name;
	    private final Integer quantity;
	    private final Double price;

	    private Itm(final String name, final Integer quantity, final Double price) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	    }
	}

	private Currency currency;
	private String consumerEmail;
	private String consumerName;
	private PhoneNumber consumerPhone;
	private LocalizationLanguage consumerPreferLanguage;
	private TaxpayerNumber consumerTaxpayerNumber;
	private String externalId;
	private List<Itm> itms = new ArrayList<>();
	private String number;
	private Predicate<String> numberIsUniqueTest;
	private Instant created;

	private InvoiceBuilder() {
	}

	public InvoiceBuilder withNumber(final String number) {
	    this.number = MyStrings.requireNonEmpty(number, "number");
	    this.numberIsUniqueTest = null;
	    return this;
	}

	public InvoiceBuilder withNumber(final String number, final Predicate<String> numberIsUniqueTest) {
	    this.number = MyStrings.requireNonEmpty(number, "number");
	    this.numberIsUniqueTest = MyObjects.requireNonNull(numberIsUniqueTest, "numberIsUniqueTest");
	    return this;
	}

	public InvoiceBuilder withCreationInstant(final Instant created) {
	    this.created = MyObjects.requireNonNull(created, "created");
	    return this;
	}

	public InvoiceBuilder withGeneratedNumber(final Predicate<String> numberIsUniqueTest) {
	    this.number = null;
	    this.numberIsUniqueTest = MyObjects.requireNonNull(numberIsUniqueTest, "numberIsUniqueTest");
	    return this;
	}

	public InvoiceBuilder withGeneratedNumber() {
	    this.number = null;
	    this.numberIsUniqueTest = null;
	    return this;
	}

	public InvoiceBuilder testingNumberWith(final Predicate<String> numberIsUniqueTest) {
	    this.numberIsUniqueTest = MyObjects.requireNonNull(numberIsUniqueTest, "numberIsUniqueTest");
	    return this;
	}

	public InvoiceBuilder withCurrency(final Currency currency) {
	    this.currency = Objects.requireNonNull(currency, "currency");
	    return this;
	}

	public InvoiceBuilder withConsumerPreferLanguage(final LocalizationLanguage consumerPreferLanguage) {
	    this.consumerPreferLanguage = MyObjects.requireNonNull(consumerPreferLanguage, "consumerPreferLanguage");
	    return this;
	}

	public InvoiceBuilder withConsumerName(final String consumerName) {
	    this.consumerName = MyStrings.requireNonEmpty(consumerName, "consumerName");
	    return this;
	}

	public InvoiceBuilder withConsumerEmail(final String consumerEmail) {
	    this.consumerEmail = MyStrings.requireNonEmpty(consumerEmail, "consumerEmail");
	    return this;
	}

	public InvoiceBuilder withConsumerEmail(Optional<String> consumerEmail) {
	    MyObjects.requireNonNull(consumerEmail, "consumerEmail") //
		    .ifPresent(this::withConsumerEmail);
	    return this;
	}

	public InvoiceBuilder withConsumerPhone(final PhoneNumber consumerPhone) {
	    this.consumerPhone = MyObjects.requireNonNull(consumerPhone, "consumerPhone");
	    return this;
	}

	public InvoiceBuilder withConsumerPhone(Optional<PhoneNumber> consumerPhone) {
	    MyObjects.requireNonNull(consumerPhone, "consumerPhone") //
		    .ifPresent(this::withConsumerPhone);
	    return this;
	}

	public InvoiceBuilder withConsumerTaxpayerNumber(final TaxpayerNumber consumerTaxpayerNumber) {
	    this.consumerTaxpayerNumber = MyObjects.requireNonNull(consumerTaxpayerNumber, "consumerTaxpayerNumber");
	    return this;
	}

	public InvoiceBuilder withConsumerTaxpayerNumber(Optional<TaxpayerNumber> consumerTaxpayerNumber) {
	    MyObjects.requireNonNull(consumerTaxpayerNumber, "consumerTaxpayerNumber") //
		    .ifPresent(this::withConsumerTaxpayerNumber);
	    return this;
	}

	public InvoiceBuilder withItem(final String name, final Integer quantity, final Double price) {
	    MyStrings.requireNonEmpty(name, "name");
	    MyNumbers.requirePositive(quantity, "quantity");
	    MyNumbers.requirePositive(price, "price");
	    itms.add(new Itm(name, quantity, price));
	    return this;
	}

	public InvoiceBuilder clearItems() {
	    itms = new ArrayList<>();
	    return this;
	}

	public InvoiceBuilder withExternalId(final Optional<String> externalId) {
	    MyObjects.requireNonNull(externalId, "externalId") //
		    .ifPresent(this::withExternalId);
	    return this;
	}

	public InvoiceBuilder withExternalId(final String externalId) {
	    this.externalId = MyStrings.requireNonEmpty(externalId, "externalId");
	    return this;
	}

	public InvoiceBuilder withExternalId(final Number externalId) {
	    this.externalId = String.valueOf(MyNumbers.requireNonZero(externalId));
	    return this;
	}

	public Invoice build() throws NonUniqueNumberException, NumberOfAttemptsExceedException {
	    final Invoice invoice = new Invoice();

	    MyOptionals.of(created).ifPresent(x -> invoice.created = x);

	    if (MyStrings.empty(number)) {
		// using generated value
		invoice.number = MyObjects.nonNull(numberIsUniqueTest) //
			? generateNumber(numberIsUniqueTest) //
			: generateNumber();
	    } else {
		// using user value
		if (MyObjects.nonNull(numberIsUniqueTest) && !numberIsUniqueTest.test(number))
		    throw new NonUniqueNumberException(String.format("The number is non-unique (%1$s)", number));
		invoice.number = number;
	    }

	    invoice.currency = MyObjects.requireNonNull(currency, "currency");
	    invoice.items = MyOptionals.streamOf(itms) //
		    .orElseThrow(() -> new IllegalArgumentException(
			    "An invoice must contains at least one item")) //
		    .map(i -> {
			Item r = new Item();
			r.invoice = invoice;
			r.name = i.name;
			r.price = i.price;
			r.quantity = i.quantity;
			r.invoice = invoice;
			return r;
		    }) //
		    .collect(Collectors.toList());
	    invoice.consumerName = MyStrings.requireNonEmpty(consumerName, "consumerName");
	    invoice.consumerPreferLanguage = MyObjects.requireNonNull(consumerPreferLanguage, "consumerPreferLanguage");
	    invoice.consumerEmail = consumerEmail;
	    invoice.consumerPhone = consumerPhone;
	    invoice.consumerTaxpayerNumber = consumerTaxpayerNumber;
	    invoice.externalId = externalId;
	    return invoice;
	}
    }

    @Override
    public String localized(final LocalizationVariant variant, final Locale locale) {
	final StringBuilder sb = new StringBuilder();

	sb.append(Localization.INVOICE_NAME.localized(variant, locale));

	final StringJoiner sj = new StringJoiner(", ", " ", "");
	sj.setEmptyValue("");

	MyOptionals.of(number) //
		.map(Localization.FIELD_NUMBER.fieldAsCaptionMapper(variant, locale)) //
		.ifPresent(sj::add);

	MyOptionals.of(getStatus()) //
		.map(Localized.toLocalizedMapper(variant, locale))//
		.map(Localization.FIELD_STATUS.fieldAsCaptionMapper(variant, locale)) //
		.ifPresent(sj::add);

	MyOptionals.of(created) //
		.map(Localizeds.instantMapper(locale)) //
		.map(Localization.FIELD_CREATED.fieldAsCaptionMapper(variant, locale)) //
		.ifPresent(sj::add);

	if (currency != null) {
	    NumberFormat nf = NumberFormat.getCurrencyInstance();
	    nf.setCurrency(currency);
	    sj.add(Localization.INVOICE_FIELD_AMOUNT.fieldAsCaptionMapper(variant, locale)
		    .apply(nf.format(getAmount())));
	}

	return sb.append(sj.toString()) //
		.append(appendEntityId()) //
		.toString();
    }

    // created (required)

    protected Instant created = Instant.now();

    public Instant getCreated() {
	return created;
    }

    // number (required)

    protected String number = generateNumber();

    public String getNumber() {
	return number;
    }

    // currency (required)

    protected Currency currency;

    public Currency getCurrency() {
	return currency;
    }

    // items

    protected List<Item> items = new ArrayList<>();

    public List<Item> getItems() {
	return Collections.unmodifiableList(items);
    }

    // consumerName (required)

    protected String consumerName;

    public String getConsumerName() {
	return consumerName;
    }

    // consumerPreferLanguage (required)

    protected LocalizationLanguage consumerPreferLanguage;

    public LocalizationLanguage getConsumerPreferLanguage() {
	return consumerPreferLanguage;
    }

    // consumerEmail (optional)

    protected String consumerEmail;

    public String getConsumerEmail() {
	return consumerEmail;
    }

    public Optional<String> optionalConsumerEmail() {
	return MyOptionals.of(consumerEmail);
    }

    // consumerPhone (optional)

    protected PhoneNumber consumerPhone;

    public PhoneNumber getConsumerPhone() {
	return consumerPhone;
    }

    public Optional<PhoneNumber> optionalConsumerPhone() {
	return MyOptionals.of(consumerPhone);
    }

    // consumerTaxpayerNumber (optional)

    protected TaxpayerNumber consumerTaxpayerNumber;

    public TaxpayerNumber getConsumerTaxpayerNumber() {
	return consumerTaxpayerNumber;
    }

    public Optional<TaxpayerNumber> optionalConsumerTaxpayerNumber() {
	return MyOptionals.of(consumerTaxpayerNumber);
    }

    // externalId (optional)

    protected String externalId;

    public String getExternalId() {
	return externalId;
    }

    public Optional<String> optionalExternalId() {
	return MyOptionals.of(externalId);
    }

    // payment

    protected Payment payment;

    public Payment getPayment() {
	return payment;
    }

    public Optional<Payment> optionalPayment() {
	return MyOptionals.of(getPayment());
    }

    public boolean isPaid() {
	return optionalPayment().isPresent();
    }

    public Invoice requireNotPaid() throws IllegalStateException {
	if (isPaid())
	    throw MyExceptions.illegalStateFormat(IsPaidException::new, "Is paid '%1$s'", this);
	return this;
    }

    public Invoice requirePaid() throws IllegalStateException {
	if (!isPaid())
	    throw MyExceptions.illegalStateFormat(IsNotPaidException::new, "Is not paid yet '%1$s'", this);
	return this;
    }

    public boolean isPending() {
	return !isExpired() && !isPaid();
    }

    public Invoice requirePending() throws IllegalStateException {
	if (!isPending())
	    throw MyExceptions.illegalStateFormat(IsNotPendingException::new,
		    "Is not pending '%1$s'. It could be expired or paid.", this);
	return this;
    }

    // expired

    protected Instant expired;

    public boolean isExpired() {
	return MyOptionals.of(expired).isPresent();
    }

    public Invoice requireExpired() {
	if (!isExpired())
	    throw MyExceptions.illegalStateFormat(IsNotExpiredException::new, "Is not expired '%1$s'", this);
	return this;
    }

    // status

    public InvoiceStatus getStatus() {
	if (isPaid())
	    return InvoiceStatus.PAID;
	if (isExpired())
	    return InvoiceStatus.EXPIRED;
	return InvoiceStatus.PENDING;
    }

    // OTHERS

    public Double getAmount() {
	return MyOptionals.streamOf(getItems())
		.orElseGet(Stream::empty)
		.mapToDouble(Item::getTotal)
		.sum();
    }

    // controllers

    @Override
    public void unlazy() {
	MyOptionals.of(getPayment()).ifPresent(Entity::unlazy);
	getAmount(); // also fetches 'items'
    }

    public synchronized void expire() {
	requireNotPaid();
	expired = Instant.now();
    }

    public synchronized Invoice paidBy(final Payment payment) {
	MyObjects.requireNonNull(payment, "payment");

	synchronized (payment) {
	    requirePending();

	    if (payment.optionalForInvoice().isPresent())
		throw MyExceptions.illegalStateFormat("Payment already has invoice attached");

	    if (optionalPayment().isPresent())
		throw MyExceptions.illegalStateFormat("Invoice already has payment attached");

	    // // TODO FEAUTURE : Need to implement more Invoice validation
	    // points

	    this.payment = payment;
	    payment.forInvoice = this;

	    return this;
	}
    }
}
