package test.builder;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Currency;

import org.junit.BeforeClass;
import org.junit.Test;

import tech.lapsa.epayment.domain.QazkomPayment;
import tech.lapsa.epayment.qazkom.QazkomConstants;
import tech.lapsa.java.commons.io.MyResources;
import tech.lapsa.java.commons.security.MyCertificates;
import tech.lapsa.java.commons.security.MyKeyStores;
import tech.lapsa.java.commons.security.MyKeyStores.StoreType;

public class QazkomPaymentBuilderTest {

    private static final StoreType STORETYPE = StoreType.JKS;
    private static final String KEYSTORE = "/kkb.jks";
    private static final String STOREPASS = "1q2w3e4r";
    private static final String ALIAS = "kkbca-test";

    private static X509Certificate bankCert;

    @BeforeClass
    public static void loadKeys() throws Exception {

	final InputStream storeStream = MyResources.optAsStream(QazkomPaymentBuilderTest.class, KEYSTORE) //
		.orElseThrow(() -> new RuntimeException("Keystore not found"));

	final KeyStore keystore = MyKeyStores.from(storeStream, STORETYPE, STOREPASS) //
		.orElseThrow(() -> new RuntimeException("Can not load keystore"));

	bankCert = MyCertificates.from(keystore, ALIAS) //
		.orElseThrow(() -> new RuntimeException("Can find key entry"));
    }

    private static final String BANK_XML = ""
	    + "<bank name=\"Kazkommertsbank JSC\">"
	    + "<customer name=\"MR CARD\" mail=\"vadim.isaev@me.com\" phone=\"\">"
	    + "<merchant cert_id=\"c183d70b\" name=\"Test shop 3\">"
	    + "<order order_id=\"484902574738032\" amount=\"2382.05\" currency=\"398\">"
	    + "<department merchant_id=\"92061103\" amount=\"2382.05\"/>"
	    + "</order>"
	    + "</merchant>"
	    + "<merchant_sign type=\"RSA\"/>"
	    + "</customer>"
	    + "<customer_sign type=\"RSA\"/>"
	    + "<results timestamp=\"2016-06-14 15:18:02\">"
	    + "<payment merchant_id=\"92061103\" card=\"440564-XX-XXXX-6150\" amount=\"2382.05\" "
	    + "reference=\"160614151802\" approval_code=\"151802\" response_code=\"00\" Secure=\"No\" "
	    + "card_bin=\"\" c_hash=\"13988BBF7C6649F799F36A4808490A3E\"/>"
	    + "</results>"
	    + "</bank>";

    private static final String PAYMENT_XML = ""
	    + "<document>"
	    + BANK_XML
	    + "<bank_sign cert_id=\"c183d690\" type=\"SHA/RSA\">"
	    + "uHWuUQ938FNwU3ZkEip2/HGSL6niFomLvmk"
	    + "lkg1mWOiGPCzEcQoFc5XFfTYnLwin3qtl3JsnO/yysFAjXF"
	    + "OBYfe5txQIWo5rnCzQ7/97n7jDHUHx58rqTLPzWwb70bYE3DuZch/cvS2gyFBbstUkzik+0YDJ/FuMwmTU4Kl/dBc="
	    + "</bank_sign>"
	    + "</document>";

    public static QazkomPayment payment() {
	return QazkomPayment.builder() //
		.fromRawXml(PAYMENT_XML) //
		.withBankCertificate(bankCert) //
		.build();
    }

    @Test
    public void simpleTest() {
	final Currency CURRENCY = Currency.getInstance("KZT");
	final Double AMOUNT = 2382.05d;
	final Instant CREATED = LocalDateTime.of(2016, Month.JUNE, 14, 15, 18, 02).atZone(QazkomConstants.QAZKOM_ZONE_ID)
		.toInstant();
	final String ORDER_NUMBER = "484902574738032";
	final String REFERENCE = "160614151802";

	final QazkomPayment o = payment();
	System.out.println(o);

	assertThat(o, not(nullValue()));
	assertThat(o.getCurrency(), allOf(not(nullValue()), is(equalTo(CURRENCY))));
	assertThat(o.getAmount(), allOf(not(nullValue()), is(equalTo(AMOUNT))));
	// o.getCardNumber();
	assertThat(o.getCreated(), allOf(not(nullValue()), is(equalTo(CREATED))));
	assertThat(o.getOrderNumber(), allOf(not(isEmptyOrNullString()), is(equalTo(ORDER_NUMBER))));
	assertThat(o.getReference(), allOf(not(isEmptyOrNullString()), is(equalTo(REFERENCE))));
	assertThat(o.getPaymentDoc(), not(nullValue()));
	assertThat(o.getPaymentDoc().getRawXml(), allOf(not(isEmptyOrNullString()), is(equalTo(PAYMENT_XML))));
	o.getPayerEmail();
	o.getPayerName();
	o.getPayerPhoneNumber();
    }
}
