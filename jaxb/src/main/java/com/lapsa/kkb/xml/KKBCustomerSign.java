package com.lapsa.kkb.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "customer_sign")
public class KKBCustomerSign extends KKBGenericSign implements Serializable {
    private static final long serialVersionUID = 6758256294210679603L;
}