package com.lapsa.kkb.mesenger;

import com.lapsa.kkb.core.KKBOrder;

public interface Notifier {
    void assignRequestNotification(NotificationChannel channel, NotificationRecipientType recipientType,
	    NotificationRequestStage stage,
	    KKBOrder order) throws NotificationFailed;
}
