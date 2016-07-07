package com.lapsa.kkb.persistence;

import java.util.List;

import com.lapsa.kkb.core.KKBNotification;

public interface KKBNotificationDAO extends KKBDAO<KKBNotification, Integer> {
    List<KKBNotification> findAllPending();
}
