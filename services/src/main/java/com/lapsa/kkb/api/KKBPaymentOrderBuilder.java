package com.lapsa.kkb.api;

import com.lapsa.fin.FinCurrency;
import com.lapsa.kkb.core.KKBPaymentOrder;

public interface KKBPaymentOrderBuilder {
    String generateNewOrderId();

    KKBPaymentOrder buildNewPayment(double amount);

    KKBPaymentOrder buildNewPayment(double amount, FinCurrency currency);

    KKBPaymentOrder buildNewPayment(String orderId, double amount);

    KKBPaymentOrder buildNewPayment(String orderId, double amount, FinCurrency currency);
}
