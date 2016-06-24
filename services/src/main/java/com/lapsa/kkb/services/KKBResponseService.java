package com.lapsa.kkb.services;

import com.lapsa.kkb.core.KKBOrder;
import com.lapsa.kkb.core.KKBPaymentRequestDocument;
import com.lapsa.kkb.core.KKBPaymentResponseDocument;

public interface KKBResponseService {
    String parseOrderId(String response) throws KKBServiceError, KKBFormatException;

    String parseOrderId(KKBPaymentResponseDocument response) throws KKBServiceError, KKBFormatException;

    void validateSignature(String response) throws KKBServiceError, KKBFormatException, KKBWrongSignature;

    void validateResponseXmlFormat(KKBPaymentResponseDocument response) throws KKBFormatException;
    
    void validateSignature(KKBPaymentResponseDocument response)
	    throws KKBServiceError, KKBFormatException, KKBWrongSignature;

    void validateResponse(KKBPaymentRequestDocument request, KKBPaymentResponseDocument response)
	    throws KKBFormatException, KKBValidationErrorException;

    void validateResponse(KKBOrder order) throws KKBFormatException, KKBValidationErrorException;
}
