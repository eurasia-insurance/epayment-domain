package com.lapsa.kkb.persistence;

import java.util.List;

import com.lapsa.kkb.core.KKBOrder;
import com.lapsa.kkb.core.KKBPaymentSuccessNotification;

public interface KKBPaymentSuccessNotificationDAO extends BaseKKBNotificationDAO<KKBPaymentSuccessNotification> {
    List<KKBPaymentSuccessNotification> findByKKBOrder(KKBOrder order);
}
