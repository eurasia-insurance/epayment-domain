package test.com.lapsa.kkb.xml;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.Test;

import com.lapsa.kkb.xml.KKBBank;
import com.lapsa.kkb.xml.KKBDepartment;
import com.lapsa.kkb.xml.KKBDocument;
import com.lapsa.kkb.xml.KKBMerchant;
import com.lapsa.kkb.xml.KKBMerchantSign;
import com.lapsa.kkb.xml.KKBOrder;
import com.lapsa.kkb.xml.KKBSignType;

public class KKBDocumentTest2 {

    private static final String EXAMPLE_DOCUMENT_AUTH_XML = "/example-document-auth-response-success.xml";

    private JAXBContext jaxbContext;

    @Before
    public void init() throws JAXBException {
	jaxbContext = JAXBContext.newInstance(KKBMerchant.class, KKBBank.class, KKBDocument.class);
    }

    @Test
    public void testLoadDocument() throws JAXBException {
	System.out.println();
	System.out.println("Loaded document");
	KKBDocument loaded = loadDocument(EXAMPLE_DOCUMENT_AUTH_XML);
	System.out.println(loaded.getBank().getCustomerSign());
	
	dumpDocument(loaded, true);
    }

    @SuppressWarnings("unused")
    private String getBankString(KKBDocument document) throws JAXBException {
	Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
	jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
	StringWriter sw = new StringWriter();
	jaxbMarshaller.marshal(document.getBank(), sw);
	return sw.toString();
    }

    private void dumpDocument(KKBDocument document, boolean formatted) throws JAXBException {
	System.out.println(getDocumentString(document, formatted));
    }

    private String getDocumentString(KKBDocument document, boolean formatted) throws JAXBException {
	Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted);
	jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
	StringWriter sw = new StringWriter();
	jaxbMarshaller.marshal(document, sw);
	return sw.toString();
    }

    private KKBDocument loadDocument(String resourceName) throws JAXBException {
	File resourceFile = new File(KKBDocument.class.getResource(resourceName).getFile());
	Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	KKBDocument document = (KKBDocument) jaxbUnmarshaller.unmarshal(resourceFile);
	return document;
    }
}
