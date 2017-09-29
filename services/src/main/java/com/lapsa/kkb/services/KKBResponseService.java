package com.lapsa.kkb.services;

import java.time.Instant;

import com.lapsa.kkb.core.KKBOrder;
import com.lapsa.kkb.core.KKBPaymentRequestDocument;
import com.lapsa.kkb.core.KKBPaymentResponseDocument;

public interface KKBResponseService {

    String parseOrderId(String response) throws KKBServiceError, KKBFormatException;
    String parseOrderId(KKBPaymentResponseDocument response) throws KKBServiceError, KKBFormatException;
    String parseOrderId(KKBPaymentResponseDocument response, boolean formatVerified);

    String parsePaymentReferences(String response) throws KKBServiceError, KKBFormatException;
    String parsePaymentReferences(KKBPaymentResponseDocument response) throws KKBServiceError, KKBFormatException;
    String parsePaymentReferences(KKBPaymentResponseDocument response, boolean formatVerified);

    Instant parsePaymentTimestamp(KKBPaymentResponseDocument response) throws KKBServiceError, KKBFormatException;
    Instant parsePaymentTimestamp(String response) throws KKBServiceError, KKBFormatException;
    Instant parsePaymentTimestamp(KKBPaymentResponseDocument response, boolean formatVerified);

    void validateSignature(KKBPaymentResponseDocument response)
	    throws KKBServiceError, KKBFormatException, KKBWrongSignature;
    void validateSignature(String response) throws KKBServiceError, KKBFormatException, KKBWrongSignature;
    void validateSignature(KKBPaymentResponseDocument response, boolean formatVerified)
	    throws KKBServiceError, KKBWrongSignature;

    void validateResponseXmlFormat(KKBPaymentResponseDocument response) throws KKBFormatException;

    void validateResponse(KKBPaymentRequestDocument request, KKBPaymentResponseDocument response)
	    throws KKBFormatException, KKBValidationErrorException;

    void validateResponse(KKBOrder order) throws KKBFormatException, KKBValidationErrorException;
    void validateResponse(KKBOrder order, boolean formatVerified) throws KKBValidationErrorException;
}
