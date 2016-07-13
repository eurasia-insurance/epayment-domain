package com.lapsa.kkb.persistence;

import java.util.List;

import com.lapsa.kkb.core.KKBOrder;
import com.lapsa.kkb.core.KKBPaymentLinkNotification;

public interface KKBPaymentLinkNotificationDAO extends BaseKKBNotificationDAO<KKBPaymentLinkNotification> {
    List<KKBPaymentLinkNotification> findByKKBOrder(KKBOrder order);
}
