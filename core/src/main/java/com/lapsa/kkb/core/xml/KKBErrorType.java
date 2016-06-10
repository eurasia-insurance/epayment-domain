package com.lapsa.kkb.core.xml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum KKBErrorType {
    // system - ошибка при работе в системе авторизации, например неправильно
    // введеный параметр
    @XmlEnumValue(value = "system") SYSTEM,
    // auth - ошибка авторизации, в данном случае указывается код ошибки в
    // атрибуте code
    @XmlEnumValue(value = "auth") AUTH;
}
