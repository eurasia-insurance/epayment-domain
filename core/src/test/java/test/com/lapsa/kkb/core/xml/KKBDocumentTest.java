package test.com.lapsa.kkb.core.xml;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.Test;

import com.lapsa.kkb.core.xml.KKBDepartment;
import com.lapsa.kkb.core.xml.KKBDocument;
import com.lapsa.kkb.core.xml.KKBMerchant;
import com.lapsa.kkb.core.xml.KKBMerchantSign;
import com.lapsa.kkb.core.xml.KKBOrder;
import com.lapsa.kkb.core.xml.KKBSignType;

public class KKBDocumentTest {

    private static final String EXAMPLE_DOCUMENT_AUTH_XML = "/example-document-auth.xml";

    private JAXBContext jaxbContext;

    @Before
    public void init() throws JAXBException {
	jaxbContext = JAXBContext.newInstance(KKBMerchant.class, KKBDocument.class);
    }

    @Test
    public void testGenerateDocument() throws JAXBException {
	System.out.println();
	System.out.println("Generated document");
	KKBDocument generated = generateTestDocument();
	dumpDocument(generated, true);

    }

    @Test
    public void testLoadDocument() throws JAXBException {
	System.out.println();
	System.out.println("Loaded document");
	KKBDocument loaded = loadDocument(EXAMPLE_DOCUMENT_AUTH_XML);
	dumpDocument(loaded, true);
    }

    @Test
    public void testGetMerchantString() throws JAXBException {
	System.out.println();
	System.out.println("Merchant string");
	KKBDocument document = generateTestDocument();
	String merchantString = getMerchantString(document);
	System.out.println(merchantString);

    }

    private String getMerchantString(KKBDocument document) throws JAXBException {
	Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
	jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
	StringWriter sw = new StringWriter();
	jaxbMarshaller.marshal(document.getMerchant(), sw);
	return sw.toString();
    }

    private void dumpDocument(KKBDocument document, boolean formatted) throws JAXBException {
	Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted);
	jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
	jaxbMarshaller.marshal(document, System.out);
    }

    private KKBDocument loadDocument(String resourceName) throws JAXBException {
	File resourceFile = new File(KKBDocument.class.getResource(resourceName).getFile());
	Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	KKBDocument document = (KKBDocument) jaxbUnmarshaller.unmarshal(resourceFile);
	return document;
    }

    private KKBDocument generateTestDocument() {
	KKBDocument document = new KKBDocument();

	KKBMerchant merchant = new KKBMerchant();
	document.setMerchant(merchant);
	merchant.setCertificateSerialId("00c183d70b");
	merchant.setName("Shop Name");

	KKBOrder order = new KKBOrder();
	merchant.setOrders(new ArrayList<>());
	merchant.getOrders().add(order);
	order.setOrderId("000282");
	order.setAmount(3100);
	order.setCurrencyCode(398);

	KKBDepartment department = new KKBDepartment();
	order.setDepartments(new ArrayList<>());
	order.getDepartments().add(department);
	department.setMerchantId("92061101");
	department.setAmount(1300);
	department.setPhone("22233355");
	department.setAirticketBookingNumber("ASDFG");

	KKBMerchantSign sign = new KKBMerchantSign();
	document.setMerchantSign(sign);
	sign.setType(KKBSignType.RSA);
	sign.setDigest(new byte[] { -89, 110, 98, -42, -75, 7, -19, 43, 103, -124, -25, -25, -112, 116, -114, 30, 11,
		-82, 60, -57, -113, 104, 101, -19, -120, -15, -124, 58, -78, 68, -31, -70, 31, -42, 1, 85, 31, 95, 102,
		-124, 60, -121, -115, -11, -102, -24, -25, -119, -13, 71, -30, 119, 43, -43, 127, 85, -8, 123, 12, -113,
		-45, 33, -72, 27, 18, 82, -105, 69, 114, 95, 62, -2, 60, -46, -20, 110, -51, 101, 37, -18, 54, 119, 87,
		-30, -58, -6, 51, -124, -108, -110, 96, -34, -97, 3, 19, 13, -14, 24, 67, -122, -34, -110, -13, 86, 10,
		-36, 102, -61, 76, 78, -58, 31, -113, 124, -91, 79, 71, 26, -31, -128, -96, 53, 99, 7, -29, 109, -84,
		121 });
	return document;
    }

}
