package tech.lapsa.epayment.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.lapsa.international.localization.LocalizationLanguage;
import com.lapsa.international.phone.PhoneNumber;

import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.exceptions.IllegalState;
import tech.lapsa.java.commons.function.MyCollections;
import tech.lapsa.java.commons.function.MyExceptions;
import tech.lapsa.java.commons.function.MyNumbers;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.function.MyStrings;
import tech.lapsa.java.commons.localization.Localized;
import tech.lapsa.java.commons.localization.Localizeds;
import tech.lapsa.java.commons.util.MyCurrencies;
import tech.lapsa.kz.taxpayer.TaxpayerNumber;
import tech.lapsa.patterns.domain.HashCodePrime;

@Entity
@Table(name = "INVOICE")
@HashCodePrime(3)
public class Invoice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static String generateNumber() {
	return UUID.randomUUID() //
		.toString() //
	;
    }

    public static String generateNumber(final Predicate<String> numberIsUniqueTest)
	    throws IllegalArgumentException, NumberOfAttemptsExceedException {
	MyObjects.requireNonNull(numberIsUniqueTest, "numberIsUniqueTest");
	final int NUMBER_OF_ATTEMPTS = 100;
	int attempt = 0;
	String number;
	do {
	    number = generateNumber();
	    if (attempt++ > NUMBER_OF_ATTEMPTS)
		throw MyExceptions.format(NumberOfAttemptsExceedException::new,
			"The number of generation attempts is exceed the limit (%1$d) while generating the unique number",
			NUMBER_OF_ATTEMPTS);
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

	    private Itm(final String name, final Integer quantity, final Double price)
		    throws IllegalArgumentException {
		this.name = MyStrings.requireNonEmpty(name, "name");
		this.quantity = MyNumbers.requirePositive(quantity, "quantity");
		this.price = MyNumbers.requirePositive(price, "price");
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
	private Instant created;

	private InvoiceBuilder() {
	}

	public InvoiceBuilder withNumber(final String number) throws IllegalArgumentException {
	    this.number = MyStrings.requireNonEmpty(number, "number");
	    return this;
	}

	public InvoiceBuilder withCreationInstant(final Instant created) throws IllegalArgumentException {
	    this.created = MyObjects.requireNonNull(created, "created");
	    return this;
	}

	public InvoiceBuilder withGeneratedNumber() {
	    number = null;
	    return this;
	}

	public InvoiceBuilder withCurrency(final Currency currency) throws IllegalArgumentException {
	    this.currency = MyObjects.requireNonNull(currency, "currency");
	    return this;
	}

	public InvoiceBuilder withConsumerPreferLanguage(final LocalizationLanguage consumerPreferLanguage)
		throws IllegalArgumentException {
	    this.consumerPreferLanguage = MyObjects.requireNonNull(consumerPreferLanguage, "consumerPreferLanguage");
	    return this;
	}

	public InvoiceBuilder withConsumerName(final String consumerName) throws IllegalArgumentException {
	    this.consumerName = MyStrings.requireNonEmpty(consumerName, "consumerName");
	    return this;
	}

	public InvoiceBuilder withConsumerEmail(final String consumerEmail) throws IllegalArgumentException {
	    this.consumerEmail = MyStrings.requireNonEmpty(consumerEmail,
		    "consumerEmail");
	    return this;
	}

	public InvoiceBuilder withConsumerEmail(final Optional<String> consumerEmail) throws IllegalArgumentException {
	    MyObjects.requireNonNull(consumerEmail, "consumerEmail") //
		    .ifPresent(this::withConsumerEmail);
	    return this;
	}

	public InvoiceBuilder withConsumerPhone(final PhoneNumber consumerPhone) throws IllegalArgumentException {
	    this.consumerPhone = MyObjects.requireNonNull(consumerPhone,
		    "consumerPhone");
	    return this;
	}

	public InvoiceBuilder withConsumerPhone(final Optional<PhoneNumber> consumerPhone)
		throws IllegalArgumentException {
	    MyObjects.requireNonNull(consumerPhone, "consumerPhone") //
		    .ifPresent(this::withConsumerPhone);
	    return this;
	}

	public InvoiceBuilder withConsumerTaxpayerNumber(final TaxpayerNumber consumerTaxpayerNumber)
		throws IllegalArgumentException {
	    this.consumerTaxpayerNumber = MyObjects.requireNonNull(
		    consumerTaxpayerNumber,
		    "consumerTaxpayerNumber");
	    return this;
	}

	public InvoiceBuilder withConsumerTaxpayerNumber(final Optional<TaxpayerNumber> consumerTaxpayerNumber)
		throws IllegalArgumentException {
	    MyObjects.requireNonNull(consumerTaxpayerNumber, "consumerTaxpayerNumber") //
		    .ifPresent(this::withConsumerTaxpayerNumber);
	    return this;
	}

	public InvoiceBuilder withItem(final String name, final Integer quantity, final Double price)
		throws IllegalArgumentException {
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

	public InvoiceBuilder withExternalId(final Optional<String> externalId) throws IllegalArgumentException {
	    MyObjects.requireNonNull(externalId, "externalId") //
		    .ifPresent(this::withExternalId);
	    return this;
	}

	public InvoiceBuilder withExternalId(final String externalId) throws IllegalArgumentException {
	    this.externalId = MyStrings.requireNonEmpty(externalId, "externalId");
	    return this;
	}

	public InvoiceBuilder withExternalId(final Number externalId) throws IllegalArgumentException {
	    this.externalId = String.valueOf(MyNumbers.requirePositive(externalId, "externalId"));
	    return this;
	}

	public Invoice build() throws IllegalArgumentException, NonUniqueNumberException {
	    try {
		return build(null);
	    } catch (final NumberOfAttemptsExceedException e) {
		throw new RuntimeException(e);
	    }
	}

	public Invoice build(final Predicate<String> numberIsUniqueTest)
		throws IllegalArgumentException, NonUniqueNumberException, NumberOfAttemptsExceedException {
	    final Invoice invoice = new Invoice();

	    MyOptionals.of(created).ifPresent(x -> invoice.created = x);

	    if (MyStrings.empty(number))
		// using generated value
		invoice.number = MyObjects.nonNull(numberIsUniqueTest) //
			? generateNumber(numberIsUniqueTest) //
			: generateNumber();
	    else {
		// using user value
		if (MyObjects.nonNull(numberIsUniqueTest) && !numberIsUniqueTest.test(number))
		    throw MyExceptions.format(NonUniqueNumberException::new, "The number is non-unique (%1$s)", number);
		invoice.number = number;
	    }

	    invoice.currency = MyObjects.requireNonNull(currency, "currency");
	    invoice.items = MyOptionals.streamOf(itms) //
		    .orElseThrow(MyExceptions.illegalArgumentSupplier("An invoice must contains at least one item")) //
		    .map(i -> {
			final Item r = new Item();
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

	if (MyObjects.nonNull(currency)) {
	    sj.add(Localization.INVOICE_FIELD_AMOUNT.fieldAsCaptionMapper(variant, locale)
		    .apply(MyCurrencies.formatAmount(getAmount(), currency, locale)));
	}

	return sb.append(sj.toString()) //
		.append(appendEntityId()) //
		.toString();
    }

    // created (required)

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    protected Instant created = Instant.now();

    public Instant getCreated() {
	return created;
    }

    // number (required)

    @Basic
    @Column(name = "NUMBER", unique = true)
    protected String number = generateNumber();

    public String getNumber() {
	return number;
    }

    // currency (required)

    @Basic
    @Column(name = "CURRENCY")
    protected Currency currency;

    public Currency getCurrency() {
	return currency;
    }

    // items

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "invoice", orphanRemoval = true)
    protected List<Item> items;

    public List<Item> getItems() {
	return MyCollections.unmodifiableOrEmptyList(items);
    }

    // consumerName (required)

    @Basic
    @Column(name = "CONSUMER_NAME")
    protected String consumerName;

    public String getConsumerName() {
	return consumerName;
    }

    // consumerPreferLanguage (required)

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "CONSUMER_PREFER_LANGUAGE")
    protected LocalizationLanguage consumerPreferLanguage;

    public LocalizationLanguage getConsumerPreferLanguage() {
	return consumerPreferLanguage;
    }

    // consumerEmail (optional)

    @Basic
    @Column(name = "PAYER_EMAIL")
    protected String consumerEmail;

    public String getConsumerEmail() {
	return consumerEmail;
    }

    public Optional<String> optionalConsumerEmail() {
	return MyOptionals.of(consumerEmail);
    }

    // consumerPhone (optional)

    @Basic
    @Column(name = "CONSUMER_PHONE")
    protected PhoneNumber consumerPhone;

    public PhoneNumber getConsumerPhone() {
	return consumerPhone;
    }

    public Optional<PhoneNumber> optionalConsumerPhone() {
	return MyOptionals.of(consumerPhone);
    }

    // consumerTaxpayerNumber (optional)

    @Basic
    @Column(name = "CONSUMER_TAXPAYER_NUMBER")
    protected TaxpayerNumber consumerTaxpayerNumber;

    public TaxpayerNumber getConsumerTaxpayerNumber() {
	return consumerTaxpayerNumber;
    }

    public Optional<TaxpayerNumber> optionalConsumerTaxpayerNumber() {
	return MyOptionals.of(consumerTaxpayerNumber);
    }

    // externalId (optional)

    @Basic
    @Column(name = "EXTERNAL_ID")
    protected String externalId;

    public String getExternalId() {
	return externalId;
    }

    public Optional<String> optionalExternalId() {
	return MyOptionals.of(externalId);
    }

    // payment

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "PAYMENT_ID")
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

    public Invoice requireNotPaid() throws IllegalState {
	if (isPaid())
	    throw MyExceptions.format(IllegalState::new, "Is paid '%1$s'", this);
	return this;
    }

    public Invoice requirePaid() throws IllegalState {
	if (!isPaid())
	    throw MyExceptions.format(IllegalState::new, "Is not paid yet '%1$s'", this);
	return this;
    }

    public boolean isPending() {
	return !isExpired() && !isPaid();
    }

    public Invoice requirePending() throws IllegalState {
	if (!isPending())
	    throw MyExceptions.format(IllegalState::new, "Is not pending '%1$s'. It could be expired or paid.",
		    this);
	return this;
    }

    // expired

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRED")
    protected Instant expired;

    public boolean isExpired() {
	return MyOptionals.of(expired).isPresent();
    }

    public Invoice requireExpired() throws IllegalState {
	if (!isExpired())
	    throw MyExceptions.format(IllegalState::new, "Is not expired '%1$s'", this);
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
	MyOptionals.of(getPayment()).ifPresent(BaseEntity::unlazy);
	getAmount(); // also fetches 'items'
    }

    /**
     * Изменяет статус счета как истекший. Таким образом счет не может быть
     * оплачен.
     *
     * @throws IllegalState
     *             в случае, если счет уже оплачен и не может быть отмечет как
     *             истекший
     */
    public synchronized void expire() throws IllegalState {
	requireNotPaid();
	expired = Instant.now();
    }

    /**
     * Прикрепляет платеж к счету и устанавливает связи между объектами. Таким
     * образом, статус счета становится оплаченным.
     *
     * @param payment
     *            платеж
     * @return оплаченный счет
     * @throws IllegalArgumentException
     *             если параметр переданный методу пуст (null)
     * @throws IllegalArgument
     *             если платеж <code>payment</code> не проходит проверку на
     *             консистентность
     * @throws IllegalState
     *             если счет не может быть оплачен (уже оплачен, истек срок
     *             действия)
     */
    public synchronized Invoice paidBy(final Payment payment)
	    throws IllegalArgumentException, IllegalArgument, IllegalState {

	MyObjects.requireNonNull(payment, "payment");

	synchronized (payment) {

	    if (payment.optionalForInvoice().isPresent())
		throw MyExceptions.format(IllegalArgument::new, "Payment already has invoice attached");

	    // // TODO FEAUTURE : Need to implement more Invoice validation
	    // points that throws IllegalArgument exception

	    requirePending();

	    this.payment = payment;
	    payment.forInvoice = this;

	    return this;
	}
    }
}
