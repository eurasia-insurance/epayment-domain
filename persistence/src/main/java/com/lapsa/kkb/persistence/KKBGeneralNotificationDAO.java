package com.lapsa.kkb.persistence;

import java.util.List;

import com.lapsa.kkb.core.KKBNotification;

public interface KKBGeneralNotificationDAO<T extends KKBNotification> extends KKBGeneralDAO<T, Integer> {
    List<T> findAllPending();
}
