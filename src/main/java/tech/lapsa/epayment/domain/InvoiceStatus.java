package tech.lapsa.epayment.domain;

import tech.lapsa.java.commons.localization.LocalizedElement;

public enum InvoiceStatus implements LocalizedElement {
    PENDING, PAID, EXPIRED, PAYMENT_CANCELED;
}
