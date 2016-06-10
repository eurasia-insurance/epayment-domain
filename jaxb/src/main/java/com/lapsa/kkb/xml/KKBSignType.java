package com.lapsa.kkb.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum KKBSignType {
    // RSA - RSA
    @XmlEnumValue(value = "RSA") RSA;
}
