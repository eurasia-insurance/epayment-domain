package com.lapsa.kkb.services;

public interface KKBResponseParserService {
    String parseOrderId(String response) throws KKBServiceError, KKBFormatException;
    void validateSignature(String response) throws KKBServiceError, KKBFormatException, KKBWrongSignature;
}
