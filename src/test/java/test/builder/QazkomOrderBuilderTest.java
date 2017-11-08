package test.builder;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import org.junit.BeforeClass;
import org.junit.Test;

import com.lapsa.fin.FinCurrency;
import com.lapsa.international.localization.LocalizationLanguage;

import tech.lapsa.epayment.domain.Invoice;
import tech.lapsa.epayment.domain.QazkomOrder;
import tech.lapsa.java.commons.resources.Resources;
import tech.lapsa.java.commons.security.MyCertificates;
import tech.lapsa.java.commons.security.MyKeyStores;
import tech.lapsa.java.commons.security.MyKeyStores.StoreType;
import tech.lapsa.java.commons.security.MyPrivateKeys;
import tech.lapsa.kz.taxpayer.TaxpayerNumber;

public class QazkomOrderBuilderTest {

    private static final StoreType STORETYPE = StoreType.JKS;
    private static final String KEYSTORE = "/kkb.jks";
    private static final String STOREPASS = "1q2w3e4r";
    private static final String ALIAS = "cert";

    private static X509Certificate merchantCert;
    private static PrivateKey merchantKey;

    @BeforeClass
    public static void loadKeys() throws Exception {

	InputStream storeStream = Resources.optionalAsStream(QazkomOrderBuilderTest.class, KEYSTORE) //
		.orElseThrow(() -> new RuntimeException("Keystore not found"));

	KeyStore keystore = MyKeyStores.from(storeStream, STORETYPE, STOREPASS) //
		.orElseThrow(() -> new RuntimeException("Can not load keystore"));

	merchantKey = MyPrivateKeys.from(keystore, ALIAS, STOREPASS) //
		.orElseThrow(() -> new RuntimeException("Can't find key entry"));

	merchantCert = MyCertificates.from(keystore, ALIAS) //
		.orElseThrow(() -> new RuntimeException("Can find key entry"));
    }

    private static final String CART_XML = ""
	    + "<document>"
	    + "<item name=\"Apple iPhone X\" number=\"1\" quantity=\"1\" amount=\"1000\"/>"
	    + "<item name=\"Apple MacBook Pro\" number=\"2\" quantity=\"1\" amount=\"2000\"/>"
	    + "</document>";

    private static final String ORDER_XML = ""
	    + "<document>"
	    + "<merchant cert_id=\"c183d70b\" name=\"Test shop 3\">"
	    + "<order order_id=\"617300137516891\" currency=\"398\" amount=\"3000\">"
	    + "<department merchant_id=\"92061103\" amount=\"3000\"/>"
	    + "</order>"
	    + "</merchant>"
	    + "<merchant_sign type=\"RSA\">"
	    + "GnHQXZWHwLBzEi2ReAVwZ2V2rUtTLLQdxUn1JCA4ISZAQdQ2n+3nk/pCge7"
	    + "6yxx+sSO1OjeT4oLgQ5kUKKVsV4DPK4Qy7TWN7UstAb9WLietn2q3XB3VhA"
	    + "P53z4PW2TGJZQvHR14Pluvb6hp+Y8EI51iv4JMj730/tWztbnXy0c="
	    + "</merchant_sign>"
	    + "</document>";

    @Test
    public void simpleTest() {
	QazkomOrder o = QazkomOrder.builder() //
		.forInvoice(Invoice.builder() //
			.withCurrencty(FinCurrency.KZT) //
			.withConsumer("John Bull", "john.bull@mail.com", LocalizationLanguage.RUSSIAN,
				TaxpayerNumber.of("800225000319")) //
			.withExternalId("123") //
			.withItem("Apple iPhone X", 1, 1000d) //
			.withItem("Apple MacBook Pro", 1, 2000d) //
			.build()) //
		.withMerchant("92061103", "Test shop 3", merchantCert, merchantKey) //
		.build();

	assertThat(o, not(nullValue()));
	assertThat(o.getAmount(), allOf(not(nullValue()), equalTo(3000d)));
	assertThat(o.getForInvoice(), not(nullValue()));
	assertThat(o.getOrderNumber(), not(isEmptyOrNullString()));
	assertThat(o.getOrderDoc(), not(nullValue()));
	assertThat(o.getOrderDoc().getRawXml(), allOf(not(isEmptyOrNullString()), equalTo(ORDER_XML)));
	assertThat(o.getCartDoc(), not(nullValue()));
	assertThat(o.getCartDoc().getRawXml(), allOf(not(isEmptyOrNullString()), equalTo(CART_XML)));
    }

}
