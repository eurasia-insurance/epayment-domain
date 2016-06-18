package com.lapsa.kkb.core;

public enum KKBPaymentStatus {
    NEW, // новый
    SENT, // отправлен
    ACCEPTED, // принят и подтвержден
    DECLINED, // отклонен платежной системой
    ENROLLED, // проведен по счету
    COMPLETED, // закрыт
    ;
    //
}
