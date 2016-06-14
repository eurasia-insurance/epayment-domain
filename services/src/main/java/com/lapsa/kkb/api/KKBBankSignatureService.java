package com.lapsa.kkb.api;

import java.security.cert.X509Certificate;

public interface KKBBankSignatureService {
    void verify(byte[] data, byte[] signature) throws KKBSignatureOperationFailed, KKBWrongSignature;

    void verify(byte[] data, byte[] signature, boolean inverted) throws KKBSignatureOperationFailed, KKBWrongSignature;

    X509Certificate getBankCertificate();
}
