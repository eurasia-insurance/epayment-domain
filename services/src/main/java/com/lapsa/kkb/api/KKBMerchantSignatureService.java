package com.lapsa.kkb.api;

import java.security.cert.X509Certificate;

public interface KKBMerchantSignatureService {
    byte[] sign(byte[] data) throws KKBSignatureOperationFailed;

    byte[] sign(byte[] data, boolean inverted) throws KKBSignatureOperationFailed;

    void verify(byte[] data, byte[] signature) throws KKBSignatureOperationFailed, KKBWrongSignature;

    void verify(byte[] data, byte[] signature, boolean inverted) throws KKBSignatureOperationFailed, KKBWrongSignature;

    X509Certificate getMerchantCertificate();
}
