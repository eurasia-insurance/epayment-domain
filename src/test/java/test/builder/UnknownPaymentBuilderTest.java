package test.builder;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Currency;

import org.junit.Test;

import tech.lapsa.epayment.domain.Invoice;
import tech.lapsa.epayment.domain.UnknownPayment;
import tech.lapsa.epayment.qazkom.QazkomConstants;

public class UnknownPaymentBuilderTest {

    @Test
    public void basicTest() {
	final Currency CURRENCY = Currency.getInstance("KZT");
	final Double AMOUNT = 2382.05d;
	final Instant CREATED = LocalDateTime.of(2016, Month.JUNE, 14, 15, 18, 02).atZone(QazkomConstants.QAZKOM_ZONE_ID)
		.toInstant();

	final UnknownPayment o = UnknownPayment.builder() //
		.withAmount(AMOUNT) //
		.withCurrency(CURRENCY) //
		.withCreationInstant(CREATED) //
		.build();
	System.out.println(o);

	assertThat(o, not(nullValue()));
	assertThat(o.getCurrency(), allOf(not(nullValue()), is(equalTo(CURRENCY))));
	assertThat(o.getAmount(), allOf(not(nullValue()), is(equalTo(AMOUNT))));
	assertThat(o.getCreated(), allOf(not(nullValue()), is(equalTo(CREATED))));
    }
    
    @Test
    public void fromInvoice() {
	Invoice i = InvoiceBuilderTest.invoice();
	
	UnknownPayment o = UnknownPayment.forInvoice(i).build();
	System.out.println(o);

	assertThat(o, not(nullValue()));
	assertThat(o.getCurrency(), allOf(not(nullValue()), is(equalTo(InvoiceBuilderTest.INVOICE_CURRENCY))));
	assertThat(o.getAmount(), allOf(not(nullValue()), is(equalTo(InvoiceBuilderTest.INVOICE_AMOUNT))));
	assertThat(o.getCreated(), not(nullValue()));
    }
}
