package com.lapsa.kkb.services;

import com.lapsa.fin.FinCurrency;
import com.lapsa.kkb.core.KKBPaymentOrder;

public interface KKBPaymentOrderFactory {
    String generateNewOrderId();

    KKBPaymentOrder buildNewPaymentOrder(double amount);

    KKBPaymentOrder buildNewPaymentOrder(double amount, FinCurrency currency);

    KKBPaymentOrder buildNewPaymentOrder(String orderId, double amount);

    KKBPaymentOrder buildNewPaymentOrder(String orderId, double amount, FinCurrency currency);
}
