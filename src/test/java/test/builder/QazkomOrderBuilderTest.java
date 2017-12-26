package test.builder;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import org.junit.BeforeClass;
import org.junit.Test;

import tech.lapsa.epayment.domain.QazkomOrder;
import tech.lapsa.java.commons.io.MyResources;
import tech.lapsa.java.commons.security.MyCertificates;
import tech.lapsa.java.commons.security.MyKeyStores;
import tech.lapsa.java.commons.security.MyKeyStores.StoreType;
import tech.lapsa.java.commons.security.MyPrivateKeys;

public class QazkomOrderBuilderTest {

    private static final StoreType STORETYPE = StoreType.JKS;
    private static final String KEYSTORE = "/kkb.jks";
    private static final String STOREPASS = "1q2w3e4r";
    private static final String ALIAS = "cert";

    private static X509Certificate merchantCert;
    private static PrivateKey merchantKey;

    @BeforeClass
    public static void loadKeys() throws Exception {

	final InputStream storeStream = MyResources.optAsStream(QazkomOrderBuilderTest.class, KEYSTORE) //
		.orElseThrow(() -> new RuntimeException("Keystore not found"));

	final KeyStore keystore = MyKeyStores.from(storeStream, STORETYPE, STOREPASS) //
		.orElseThrow(() -> new RuntimeException("Can not load keystore"));

	merchantKey = MyPrivateKeys.from(keystore, ALIAS, STOREPASS) //
		.orElseThrow(() -> new RuntimeException("Can't find key entry"));

	merchantCert = MyCertificates.from(keystore, ALIAS) //
		.orElseThrow(() -> new RuntimeException("Can find key entry"));
    }

    private static final String CART_XML = ""
	    + "<document>"
	    + "<item name=\"Apple iPhone X\" number=\"1\" quantity=\"1\" amount=\"1000\"/>"
	    + "<item name=\"Apple MacBook Pro\" number=\"2\" quantity=\"1\" amount=\"1382.05\"/>"
	    + "</document>";

    private static final String ORDER_XML = ""
	    + "<document>"
	    + "<merchant cert_id=\"c183d70b\" name=\"Test shop 3\">"
	    + "<order order_id=\"484902574738032\" currency=\"398\" amount=\"2382.05\">"
	    + "<department merchant_id=\"92061103\" amount=\"2382.05\"/>"
	    + "</order>"
	    + "</merchant>"
	    + "<merchant_sign type=\"RSA\">"
	    + "0SeH7sjQH1U/wYRn9AKM8q1Zujjs1zMaF5M0Gm+6k4KiPG6yAXaqazBzcUU/"
	    + "LC/fMR5n4CoqFv/+MMvHaQw+htvBDH0Fe6svazqZZMQnKVQVkfXg9Z2y88xi"
	    + "pGt+daya5OK/lqTvMGh1ACgEObGv95/nXledaPDpU4oexQcaySg="
	    + "</merchant_sign>"
	    + "</document>";

    public static QazkomOrder order() {
	return QazkomOrder.builder() //
		.forInvoice(InvoiceBuilderTest.invoice()) //
		.withNumber("484902574738032") //
		.withMerchant("92061103", "Test shop 3", merchantCert, merchantKey) //
		.build();
    }

    @Test
    public void simpleTest() {
	final QazkomOrder o = order();
	System.out.println(o);

	assertThat(o, not(nullValue()));
	assertThat(o.getAmount(), allOf(not(nullValue()), equalTo(2382.05d)));
	assertThat(o.getForInvoice(), not(nullValue()));
	assertThat(o.getOrderNumber(), not(isEmptyOrNullString()));
	assertThat(o.getOrderDoc(), not(nullValue()));
	assertThat(o.getOrderDoc().getRawXml(), allOf(not(isEmptyOrNullString()), equalTo(ORDER_XML)));
	assertThat(o.getCartDoc(), not(nullValue()));
	assertThat(o.getCartDoc().getRawXml(), allOf(not(isEmptyOrNullString()), equalTo(CART_XML)));
    }

}
