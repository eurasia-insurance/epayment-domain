package com.lapsa.kkb.services;

import com.lapsa.kkb.core.KKBCartDocument;
import com.lapsa.kkb.core.KKBOrder;
import com.lapsa.kkb.core.KKBPaymentRequestDocument;

public interface KKBDocumentComposerService {
    @Deprecated
    KKBPaymentRequestDocument composeRequest(KKBOrder order) throws KKBServiceError;

    @Deprecated
    KKBCartDocument composeCart(KKBOrder order) throws KKBServiceError;
}
