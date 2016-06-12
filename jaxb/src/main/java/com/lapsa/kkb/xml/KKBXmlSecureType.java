package com.lapsa.kkb.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum KKBSecureType {
    @XmlEnumValue("Yes") SECURED_3D,
    @XmlEnumValue("No") NON_SECURED;
}
