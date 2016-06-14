package com.lapsa.kkb.api;

public interface KKBAuthorizationResponseService {
    void checkSinature(String response)
	    throws KKBWrongSignature, KKBSignatureOperationFailed, KKBResponseFormatValidationError;

    KKBAuthorization parseResponse(String response)
	    throws KKBWrongSignature, KKBSignatureOperationFailed, KKBResponseFormatValidationError;

    void validate(KKBAuthorization request, KKBAuthorization response);
}
