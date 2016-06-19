package com.lapsa.kkb.services;

import java.security.cert.X509Certificate;

import com.lapsa.kkb.core.KKBSignedData;

public interface KKBSignatureVerifierService {
    void verify(byte[] data, byte[] signature) throws KKBServiceError, KKBWrongSignature;

    void verify(byte[] data, byte[] signature, boolean inverted) throws KKBServiceError, KKBWrongSignature;

    X509Certificate getSignatureCertificate();
    
    String getSignatureAlgorithm();

    void verify(KKBSignedData signedData) throws KKBServiceError, KKBWrongSignature;
}