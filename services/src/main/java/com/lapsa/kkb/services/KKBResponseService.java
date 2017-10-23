package com.lapsa.kkb.services;

import java.time.Instant;

import com.lapsa.kkb.core.KKBOrder;
import com.lapsa.kkb.core.KKBPaymentRequestDocument;
import com.lapsa.kkb.core.KKBPaymentResponseDocument;

public interface KKBResponseService {

    //

    String parseOrderId(String response) throws KKBServiceError, KKBFormatException;

    String parseOrderId(KKBPaymentResponseDocument response) throws KKBServiceError, KKBFormatException;

    default String parseOrderIdNoFormatCheck(KKBPaymentResponseDocument response) {
	try {
	    return parseOrderId(response);
	} catch (KKBFormatException e) {
	    throw new IllegalStateException("Invalid format", e);
	}
    }

    //

    String parsePaymentReferences(String response) throws KKBServiceError, KKBFormatException;

    String parsePaymentReferences(KKBPaymentResponseDocument response) throws KKBServiceError, KKBFormatException;

    default String parsePaymentReferencesNoFormatCheck(KKBPaymentResponseDocument response) {
	try {
	    return parsePaymentReferences(response);
	} catch (KKBFormatException e) {
	    throw new IllegalStateException("Invalid format", e);
	}
    }

    //

    Instant parsePaymentTimestamp(String response) throws KKBServiceError, KKBFormatException;

    Instant parsePaymentTimestamp(KKBPaymentResponseDocument response) throws KKBServiceError, KKBFormatException;

    default Instant parsePaymentTimestampNoFormatCheck(KKBPaymentResponseDocument response) {
	try {
	    return parsePaymentTimestamp(response);
	} catch (KKBFormatException e) {
	    throw new IllegalStateException("Invalid format", e);
	}
    }

    //

    void validateSignature(String response) throws KKBServiceError, KKBFormatException, KKBWrongSignature;

    void validateSignature(KKBPaymentResponseDocument response)
	    throws KKBServiceError, KKBFormatException, KKBWrongSignature;

    default void validateSignatureNoFormatCheck(KKBPaymentResponseDocument response)
	    throws KKBServiceError, KKBWrongSignature {
	try {
	    validateSignature(response);
	} catch (KKBFormatException e) {
	    throw new IllegalStateException("Invalid format", e);
	}
    }

    //

    void validateResponseXmlFormat(KKBPaymentResponseDocument response) throws KKBFormatException;

    //

    void validateResponseWithRequest(KKBPaymentRequestDocument request, KKBPaymentResponseDocument response)
	    throws KKBFormatException, KKBValidationErrorException;

    default void validateResponseWithRequestNoFormatCheck(KKBPaymentRequestDocument request,
	    KKBPaymentResponseDocument response)
	    throws KKBValidationErrorException {
	try {
	    validateResponseWithRequest(request, response);
	} catch (KKBFormatException e) {
	    throw new IllegalStateException("Invalid format", e);
	}
    }

    //

    @Deprecated
    void validateResponseWithRequest(KKBOrder order) throws KKBFormatException, KKBValidationErrorException;

    @Deprecated
    default void validateResponseWithRequestNoCheck(KKBOrder order)
	    throws KKBValidationErrorException {
	try {
	    validateResponseWithRequest(order);
	} catch (KKBFormatException e) {
	    throw new IllegalStateException("Invalid format", e);
	}
    }
}
