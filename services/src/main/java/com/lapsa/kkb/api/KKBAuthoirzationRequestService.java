package com.lapsa.kkb.api;

import com.lapsa.fin.FinCurrency;

public interface KKBAuthoirzationRequestService {
    String encodeRequest(String orderId, double amount, FinCurrency currency) throws KKBEncodingException;
    String encodeRequest(KKBAuthorizationRequest request) throws KKBEncodingException;
}
