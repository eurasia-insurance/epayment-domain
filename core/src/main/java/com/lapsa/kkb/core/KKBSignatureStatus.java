package com.lapsa.kkb.core;

import tech.lapsa.java.commons.localization.LocalizedElement;

public enum KKBSignatureStatus implements LocalizedElement {
    UNCHECKED, // непроверено
    CHECKED_VALID, // проверено и ОК
    CHECKED_INVALID, // проверено и НЕ ОК
    ;
    //
}
