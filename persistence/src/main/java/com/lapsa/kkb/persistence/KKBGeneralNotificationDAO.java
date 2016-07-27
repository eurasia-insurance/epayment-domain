package com.lapsa.kkb.persistence;

import java.util.List;

import com.lapsa.kkb.core.KKBNotification;

public interface GeneralKKBNotificationDAO<T extends KKBNotification> extends KKBDAO<T, Integer> {
    List<T> findAllPending();
}
