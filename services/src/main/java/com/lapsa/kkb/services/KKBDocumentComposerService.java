package com.lapsa.kkb.services;

import com.lapsa.kkb.core.KKBOrder;
import com.lapsa.kkb.core.KKBPaymentRequestDocument;

public interface KKBComposerService {
    KKBPaymentRequestDocument composeRequest(KKBOrder order) throws KKBServiceError;
}
