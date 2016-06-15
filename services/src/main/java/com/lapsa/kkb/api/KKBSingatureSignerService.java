package com.lapsa.kkb.api;

import com.lapsa.kkb.core.KKBSignature;

public interface KKBSingatureSignerService {
    byte[] sign(byte[] data) throws KKBSignatureOperationFailed;

    byte[] sign(byte[] data, boolean inverted) throws KKBSignatureOperationFailed;

    void signData(KKBSignature signature) throws KKBSignatureOperationFailed;
}
