package com.lapsa.kkb.services;

import com.lapsa.kkb.core2.KKBOrder;
import com.lapsa.kkb.core2.KKBPaymentRequest;

public interface KKBComposerService {
    KKBPaymentRequest composeRequest(KKBOrder order) throws KKBServiceError;
}
