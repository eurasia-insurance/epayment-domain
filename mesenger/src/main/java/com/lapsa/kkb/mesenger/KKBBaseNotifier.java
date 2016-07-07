package com.lapsa.kkb.mesenger;

import java.util.Collection;

import com.lapsa.kkb.core.KKBBaseDomain;

public interface KKBBaseNotifier<T extends KKBBaseDomain> {

    /**
     * Send the notification entity and throws exception if somethig goes wrong
     * 
     * @param enitity
     *            notifciation entity
     * @throws KKBNotificationFailed
     *             exception throws
     */
    void sendNotification(T enitity) throws KKBNotificationFailed;

    /**
     * Methods sent a collection of KKBNotification entities
     * 
     * @param enitities
     *            a collection of KKBNotification entities to sent
     * @return number of notifications that was realy sent without exceptions
     */
    int sendMultipleNotification(Collection<T> enitities);
}
