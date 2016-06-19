package com.lapsa.kkb.services;

import com.lapsa.kkb.core.KKBPaymentOrder;

public interface KKBPaymentOrderService {
    KKBPaymentOrder parseResponse(String response)
	    throws KKBServiceError, KKBFormatException;

    void validate(KKBPaymentOrder requestOrder, KKBPaymentOrder responseOrder);

    String composeRequest(KKBPaymentOrder request) throws KKBServiceError;
}
