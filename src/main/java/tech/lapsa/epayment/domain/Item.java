package tech.lapsa.epayment.domain;

import java.util.Locale;

import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.localization.Localized;

public class Item extends AEntity<Integer> {

    private static final long serialVersionUID = 1L;

    public Item() {
	super(5);
    }

    @Override
    public String localized(LocalizationVariant variant, Locale locale) {
	// TODO Auto-generated method stub
	return Item.class.getName();
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
	return MyOptionals.ofDouble(price).orElse(0d) //
		* MyOptionals.ofInt(quantity).orElse(0);
    }
}
