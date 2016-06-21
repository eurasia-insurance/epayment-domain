package com.lapsa.kkb.services;

import com.lapsa.kkb.core.KKBPaymentRequestDocument;
import com.lapsa.kkb.core.KKBPaymentResponseDocument;

public interface KKBResponseService {
    String parseOrderId(String response) throws KKBServiceError, KKBFormatException;

    String parseOrderId(KKBPaymentResponseDocument response) throws KKBServiceError, KKBFormatException;

    void validateSignature(String response) throws KKBServiceError, KKBFormatException, KKBWrongSignature;

    void validateSignature(KKBPaymentResponseDocument response)
	    throws KKBServiceError, KKBFormatException, KKBWrongSignature;

    void validateResponse(KKBPaymentRequestDocument request, KKBPaymentResponseDocument response)
	    throws KKBFormatException, KKBValidationErrorException;
}
