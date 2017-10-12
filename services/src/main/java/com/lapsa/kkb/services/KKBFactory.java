package com.lapsa.kkb.services;

import java.net.URI;

import com.lapsa.fin.FinCurrency;
import com.lapsa.kkb.core.KKBOrder;
import com.lapsa.kkb.core.KKBOrderItem;

public interface KKBFactory {
    String generateNewOrderId();

    URI generatePaymentPageUrl(String orderId);

    KKBOrder generateNewOrder(FinCurrency currency, double cost, String product);

    KKBOrder generateNewOrder(String orderId, FinCurrency currency, double cost, String product);

    KKBOrderItem generateNewOrderItem(String product, double cost, int quantity, KKBOrder order);

    KKBOrderItem generateNewOrderItem(String product, double cost, int quantity);
}
