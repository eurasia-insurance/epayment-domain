package tech.lapsa.epayment.domain;

import java.util.Locale;
import java.util.StringJoiner;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.localization.Localized;
import tech.lapsa.patterns.domain.HashCodePrime;

@Entity
@Table(name = "ITEM")
@HashCodePrime(5)
public class Item extends EntitySuperclass {

    private static final long serialVersionUID = 1L;

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

    @Basic
    @Column(name = "NAME")
    protected String name;

    public String getName() {
	return name;
    }

    // localizedName

    @Transient
    protected Localized localizedName;

    public Localized getLocalizedName() {
	return localizedName;
    }

    // price

    @Basic
    @Column(name = "PRICE")
    protected Double price;

    public Double getPrice() {
	return price;
    }

    // quantity

    @Basic
    @Column(name = "QUANTITY")
    protected Integer quantity;

    public Integer getQuantity() {
	return quantity;
    }

    // invoice

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "INVOICE_ID")
    protected Invoice invoice;

    // OTHERS

    public Double getTotal() {
	return MyOptionals.ofDouble(getPrice()).orElse(0d) //
		* MyOptionals.ofInt(getQuantity()).orElse(0);
    }
}
