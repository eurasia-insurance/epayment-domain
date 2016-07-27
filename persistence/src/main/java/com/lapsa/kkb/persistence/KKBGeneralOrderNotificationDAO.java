package com.lapsa.kkb.persistence;

import java.util.List;

import com.lapsa.kkb.core.KKBOrder;
import com.lapsa.kkb.core.KKBOrderNotification;

public interface GeneralKKBOrderNotificationDAO<T extends KKBOrderNotification> extends GeneralKKBNotificationDAO<T> {
    List<T> findByKKBOrder(KKBOrder order);
}
