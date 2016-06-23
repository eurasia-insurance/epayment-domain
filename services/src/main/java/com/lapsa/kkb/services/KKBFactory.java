package com.lapsa.kkb.services;

import com.lapsa.kkb.core.KKBPaymentResponseDocument;

public interface KKBFactory {
    String generateNewOrderId();

    KKBPaymentResponseDocument buildResponseDocument(String response) throws KKBFormatException;
}
