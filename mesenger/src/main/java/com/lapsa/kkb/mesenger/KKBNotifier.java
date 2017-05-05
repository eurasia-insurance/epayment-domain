package com.lapsa.kkb.mesenger;

import com.lapsa.kkb.core.KKBOrder;

public interface KKBNotifier {
    void assignOrderNotification(KKBNotificationChannel channel, KKBNotificationRecipientType recipientType,
	    KKBNotificationRequestStage stage,
	    KKBOrder order) throws KKBNotificationFailed;
}
