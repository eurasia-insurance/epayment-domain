package com.lapsa.kkb.mesenger;

import com.lapsa.kkb.core.KKBOrder;

public interface KKBNotifier {
    void assignRequestNotification(KKBNotificationChannel channel, KKBNotificationRecipientType recipientType,
	    KKBNotificationRequestStage stage,
	    KKBOrder order) throws KKBNotificationFailed;
}
