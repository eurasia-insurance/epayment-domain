package com.lapsa.kkb.services;

import java.net.URL;

import com.lapsa.fin.FinCurrency;
import com.lapsa.kkb.core.KKBOrder;
import com.lapsa.kkb.core.KKBOrderItem;
import com.lapsa.kkb.core.KKBPaymentResponseDocument;

public interface KKBFactory {
    String generateNewOrderId();

    URL generatePaymentPageUrl(String orderId);

    KKBPaymentResponseDocument buildResponseDocument(String response) throws KKBFormatException;

    KKBOrder generateNewOrder(FinCurrency currency, double cost, String product);

    KKBOrder generateNewOrder(String orderId, FinCurrency currency, double cost, String product);

    KKBOrderItem generateNewOrderItem(String product, double cost, int quantity, KKBOrder order);

    KKBOrderItem generateNewOrderItem(String product, double cost, int quantity);
}
