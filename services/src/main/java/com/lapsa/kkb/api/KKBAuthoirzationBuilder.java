package com.lapsa.kkb.api;

import com.lapsa.fin.FinCurrency;

public interface KKBAuthoirzationBuilder {
    String generateNewOrderId();

    KKBAuthorization buildAuthorization(double amount);

    KKBAuthorization buildAuthorization(double amount, FinCurrency currency);

    KKBAuthorization buildAuthorization(String orderId, double amount);

    KKBAuthorization buildAuthorization(String orderId, double amount, FinCurrency currency);
}
