package test.builder;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Currency;

import org.junit.Test;

import com.lapsa.international.localization.LocalizationLanguage;
import com.lapsa.international.phone.PhoneNumber;

import tech.lapsa.epayment.domain.Invoice;
import tech.lapsa.epayment.domain.Item;
import tech.lapsa.epayment.domain.UnknownPayment;
import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.exceptions.IllegalState;

public class InvoiceBuilderTest {

    public static final Currency INVOICE_CURRENCY = Currency.getInstance("KZT");
    public static final String INVOICE_CONSUMER_NAME = "John Bull";
    public static final LocalizationLanguage INVOICE_CONSUMER_LANGUAGE = LocalizationLanguage.RUSSIAN;
    public static final PhoneNumber INVOICE_CONSUMER_PHONE = PhoneNumber.of("+77019956587");
    public static final String INVOICE_CONSUMER_EMAIL = "test@email.com";
    public static final String INVOICE_EXT_ID = "123";

    public static final String INVOICE_ITEM_1_NAME = "Apple iPhone X";
    public static final int INVOICE_ITEM_1_QUANTITY = 1;
    public static final double INVOICE_ITEM_1_PRICE = 1000d;
    public static final double INVOICE_ITEM_1_AMOUNT = INVOICE_ITEM_1_QUANTITY * INVOICE_ITEM_1_PRICE;

    public static final String INVOICE_ITEM_2_NAME = "Apple MacBook Pro";
    public static final int INVOICE_ITEM_2_QUANTITY = 1;
    public static final double INVOICE_ITEM_2_PRICE = 1382.05d;
    public static final double INVOICE_ITEM_2_AMOUNT = INVOICE_ITEM_2_QUANTITY * INVOICE_ITEM_2_PRICE;

    public static final double INVOICE_AMOUNT = INVOICE_ITEM_1_AMOUNT + INVOICE_ITEM_2_AMOUNT;

    public static Invoice invoice() {
	return Invoice.builder() //
		.withCurrency(INVOICE_CURRENCY) //
		.withConsumerName(INVOICE_CONSUMER_NAME) //
		.withConsumerPreferLanguage(INVOICE_CONSUMER_LANGUAGE) //
		.withConsumerPhone(INVOICE_CONSUMER_PHONE) //
		.withConsumerEmail(INVOICE_CONSUMER_EMAIL) //
		.withExternalId(INVOICE_EXT_ID) //
		.withItem(INVOICE_ITEM_1_NAME, INVOICE_ITEM_1_QUANTITY, INVOICE_ITEM_1_PRICE) //
		.withItem(INVOICE_ITEM_2_NAME, INVOICE_ITEM_2_QUANTITY, INVOICE_ITEM_2_PRICE) //
		.build();
    }

    @Test
    public void simpleTest() {
	final Invoice o = invoice();
	System.out.println(o);

	assertThat(o, not(nullValue()));
	assertThat(o.getItems(), allOf(not(emptyCollectionOf(Item.class)), hasSize(2)));
	assertThat(o.getAmount(), allOf(not(nullValue()), equalTo(INVOICE_AMOUNT)));
    }

    @Test
    public void statusesTest() throws IllegalArgumentException, IllegalArgument, IllegalState {
	// given
	Invoice i = invoice();

	UnknownPayment o = UnknownPayment.forInvoice(i).build();
	assertTrue(i.isPending());
	assertFalse(i.isPaid());
	assertFalse(i.isCanceled());
	
	i.paidBy(o);
	assertFalse(i.isPending());
	assertTrue(i.isPaid());
	assertFalse(i.isCanceled());

	// when
	i.getPayment().cancel("Some reason");

	// then
	assertFalse(i.isPending());
	assertFalse(i.isPaid());
	assertTrue(i.isCanceled());

    }
}
