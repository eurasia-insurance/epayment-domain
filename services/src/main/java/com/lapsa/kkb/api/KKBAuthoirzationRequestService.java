package com.lapsa.kkb.api;

public interface KKBAuthoirzationRequestService {
    String encodeRequest(KKBAuthorization request) throws KKBEncodingException;
}
