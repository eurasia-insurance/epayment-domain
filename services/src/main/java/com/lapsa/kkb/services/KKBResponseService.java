package com.lapsa.kkb.services;

import java.util.Date;

import com.lapsa.kkb.core.KKBOrder;
import com.lapsa.kkb.core.KKBPaymentRequestDocument;
import com.lapsa.kkb.core.KKBPaymentResponseDocument;

public interface KKBResponseService {

    String parseOrderId(String response) throws KKBServiceError, KKBFormatException;
    String parseOrderId(KKBPaymentResponseDocument response) throws KKBServiceError, KKBFormatException;

    String parsePaymentReferences(String response) throws KKBServiceError, KKBFormatException;
    String parsePaymentReferences(KKBPaymentResponseDocument response) throws KKBServiceError, KKBFormatException;

    Date parsePaymentTimestamp(KKBPaymentResponseDocument response) throws KKBServiceError, KKBFormatException;
    Date parsePaymentTimestamp(String response) throws KKBServiceError, KKBFormatException;

    void validateSignature(KKBPaymentResponseDocument response)
	    throws KKBServiceError, KKBFormatException, KKBWrongSignature;
    void validateSignature(String response) throws KKBServiceError, KKBFormatException, KKBWrongSignature;

    void validateResponseXmlFormat(KKBPaymentResponseDocument response) throws KKBFormatException;

    void validateResponse(KKBPaymentRequestDocument request, KKBPaymentResponseDocument response)
	    throws KKBFormatException, KKBValidationErrorException;

    void validateResponse(KKBOrder order) throws KKBFormatException, KKBValidationErrorException;
}
