package com.lapsa.kkb.xml;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class KKBTimeStampXmlAdapter extends XmlAdapter<String, Date> {

    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Date unmarshal(String v) throws Exception {
	return format.parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
	return format.format(v);
    }

}
