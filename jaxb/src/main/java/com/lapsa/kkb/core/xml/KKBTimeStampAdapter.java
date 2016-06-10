package com.lapsa.kkb.core.xml;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class KKBTimeStampAdapter extends XmlAdapter<String, Date> {

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Date unmarshal(String v) throws Exception {
	return df.parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
	return df.format(v);
    }

}
