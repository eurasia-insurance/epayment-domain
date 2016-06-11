package com.lapsa.kkb.xml;

import java.util.Base64;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class KKBSignatureByteArrayXmlAdapter extends XmlAdapter<String, byte[]> {

    @Override
    public byte[] unmarshal(String v) throws Exception {
	if (v == null || v.trim().isEmpty())
	    return null;
	byte[] r = Base64.getDecoder().decode(v);
	return r;
    }

    @Override
    public String marshal(byte[] v) throws Exception {
	if (v == null || v.length == 0)
	    return null;
	return Base64.getEncoder().encodeToString(v);
    }

}
