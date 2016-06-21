package com.lapsa.kkb.services;

import com.lapsa.kkb.core.KKBPaymentRequestDocument;
import com.lapsa.kkb.core.KKBPaymentResponseDocument;

public interface KKBResponseParserService {
    String parseOrderId(String response) throws KKBServiceError, KKBFormatException;

    void validateSignature(String response) throws KKBServiceError, KKBFormatException, KKBWrongSignature;

    void validateOrderResponse(KKBPaymentRequestDocument request, KKBPaymentResponseDocument response)
	    throws KKBFormatException, KKBValidationErrorException;
}
