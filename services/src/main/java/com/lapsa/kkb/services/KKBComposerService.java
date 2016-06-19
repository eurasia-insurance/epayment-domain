package com.lapsa.kkb.services;

import com.lapsa.kkb.core.KKBOrder;
import com.lapsa.kkb.core.KKBPaymentRequest;

public interface KKBComposerService {
    KKBPaymentRequest composeRequest(KKBOrder order) throws KKBServiceError;
}
