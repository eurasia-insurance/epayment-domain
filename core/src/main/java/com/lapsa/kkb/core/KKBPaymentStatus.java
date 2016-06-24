package com.lapsa.kkb.core;

public enum KKBPaymentStatus {
    NEW, // новый
    AUTHORIZATION_PASS, // принят и подтвержден
    AUTHORIZATION_FAILED, // отклонен платежной системой
    ENROLLED, // проведен по счету
    COMPLETED, // закрыт
    ;
    //
}
