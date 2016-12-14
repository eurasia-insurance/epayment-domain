package com.lapsa.kkb.persistence;

import java.util.List;

import com.lapsa.kkb.core.KKBOrder;
import com.lapsa.kkb.core.KKBOrderNotification;

public interface KKBGeneralOrderNotificationDAO<T extends KKBOrderNotification> extends KKBGeneralNotificationDAO<T> {
    List<T> findByKKBOrder(KKBOrder order) throws KKBPeristenceOperationFailed;
}
