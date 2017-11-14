package test.builder;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.lapsa.fin.FinCurrency;
import com.lapsa.international.localization.LocalizationLanguage;

import tech.lapsa.epayment.domain.Invoice;
import tech.lapsa.epayment.domain.Item;
import tech.lapsa.kz.taxpayer.TaxpayerNumber;

public class InvoiceBuilderTest {

    public static Invoice invoice() {
	return Invoice.builder() //
		.withCurrency(FinCurrency.KZT) //
		.withConsumer("Vadim Isaev", "vadim.isaev@me.com", LocalizationLanguage.RUSSIAN,
			TaxpayerNumber.of("800225000319")) //
		.withExternalId("123") //
		.withItem("Apple iPhone X", 1, 1000d) //
		.withItem("Apple MacBook Pro", 1, 1382.05d) //
		.build();
    }

    @Test
    public void simpleTest() {
	Invoice o = invoice();

	assertThat(o, not(nullValue()));
	assertThat(o.getItems(), allOf(not(emptyCollectionOf(Item.class)), hasSize(2)));
	assertThat(o.getAmount(), allOf(not(nullValue()), equalTo(2382.05d)));
    }
}
