package com.lapsa.kkb.services;

import com.lapsa.kkb.core.KKBSignedData;

public interface KKBSingatureSignerService {
    byte[] sign(byte[] data) throws KKBSignatureOperationFailed;

    byte[] sign(byte[] data, boolean inverted) throws KKBSignatureOperationFailed;

    void signData(KKBSignedData signedData) throws KKBSignatureOperationFailed;
}
