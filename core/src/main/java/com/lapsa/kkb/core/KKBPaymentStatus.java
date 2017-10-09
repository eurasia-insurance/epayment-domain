package com.lapsa.kkb.core;

import com.lapsa.commons.elements.LocalizedElement;

public enum KKBPaymentStatus implements LocalizedElement {
    NEW, // новый
    AUTHORIZATION_PASS, // принят и подтвержден
    AUTHORIZATION_FAILED, // отклонен платежной системой
    ENROLLED, // проведен по счету
    COMPLETED, // закрыт
    CANCELED, // отменен
    ;
    //
}
