package test.unlazy;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import tech.lapsa.epayment.domain.Invoice;
import tech.lapsa.epayment.domain.QazkomOrder;
import tech.lapsa.epayment.domain.QazkomPayment;
import test.builder.InvoiceBuilderTest;
import test.builder.QazkomOrderBuilderTest;
import test.builder.QazkomPaymentBuilderTest;

public class UnlazyTest {

    @BeforeClass
    public static void init() throws Exception {
	QazkomOrderBuilderTest.loadKeys();
	QazkomPaymentBuilderTest.loadKeys();
    }

    @Test
    public void invoiceTest() {
	Invoice i = InvoiceBuilderTest.invoice();
	i.unlazy();
	assertTrue(true);
    }

    @Test
    public void orderTest() {
	QazkomOrder o = QazkomOrderBuilderTest.order();
	o.unlazy();
	assertTrue(true);
    }

    @Test
    public void paymentTest() {
	QazkomPayment p = QazkomPaymentBuilderTest.payment();
	p.unlazy();
	assertTrue(true);
    }

    @Test
    public void paidTest() {
	QazkomPayment p = QazkomPaymentBuilderTest.payment();

	Invoice i = InvoiceBuilderTest.invoice();
	i.paidBy(p);

	QazkomOrder o = QazkomOrderBuilderTest.order();
	o.paidBy(p);

	i.unlazy();
	o.unlazy();
	p.unlazy();

	assertTrue(true);
    }
}
