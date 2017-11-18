package tech.lapsa.epayment.domain;

import java.util.Locale;
import java.util.StringJoiner;

import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.localization.Localized;

public class Item extends Entity {

    private static final long serialVersionUID = 1L;
    private static final int PRIME = 5;

    @Override
    protected int prime() {
	return PRIME;
    }

    @Override
    public String localized(final LocalizationVariant variant, final Locale locale) {
	final StringBuilder sb = new StringBuilder();

	sb.append(MyOptionals.of(name) //
		.orElseGet(() -> Localization.ITEM_EMPTYNAME.localized(variant, locale)));

	final StringJoiner sj = new StringJoiner(", ", " ", "");
	sj.setEmptyValue("");

	MyOptionals.of(price) //
		.map(x -> x.toString()) //
		.map(Localization.ITEM_FIELD_PRICE.fieldAsCaptionMapper(variant, locale)) //
		.ifPresent(sj::add);

	MyOptionals.of(quantity) //
		.map(x -> x.toString()) //
		.map(Localization.ITEM_FIELD_QUANTITY.fieldAsCaptionMapper(variant, locale)) //
		.ifPresent(sj::add);

	MyOptionals.of(getTotal()) //
		.map(x -> x.toString()) //
		.map(Localization.ITEM_FIELD_TOTAL.fieldAsCaptionMapper(variant, locale)) //
		.ifPresent(sj::add);

	return sb.append(sj.toString()) //
		.append(appendEntityId()) //
		.toString();
    }

    // name

    protected String name;

    public String getName() {
	return name;
    }

    // localizedName

    protected Localized localizedName;

    public Localized getLocalizedName() {
	return localizedName;
    }

    // price

    protected Double price;

    public Double getPrice() {
	return price;
    }

    // quantity

    protected Integer quantity;

    public Integer getQuantity() {
	return quantity;
    }

    // invoice

    protected Invoice invoice;

    // OTHERS

    public Double getTotal() {
	return MyOptionals.ofDouble(getPrice()).orElse(0d) //
		* MyOptionals.ofInt(getQuantity()).orElse(0);
    }
}
