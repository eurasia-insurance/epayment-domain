package com.lapsa.kkb.core;

import java.util.Date;

public abstract class KKBNotification extends KKBBaseEntity<Integer> {
    private static final long serialVersionUID = 4682751563083710210L;
    private Date created;
    private Date updated;
    private Date sent;
    private KKBNotificationStatus status;

    // GENERATED

    public Date getCreated() {
	return created;
    }

    public void setCreated(Date created) {
	this.created = created;
    }

    public Date getUpdated() {
	return updated;
    }

    public void setUpdated(Date updated) {
	this.updated = updated;
    }

    public Date getSent() {
	return sent;
    }

    public void setSent(Date sent) {
	this.sent = sent;
    }

    public KKBNotificationStatus getStatus() {
	return status;
    }

    public void setStatus(KKBNotificationStatus status) {
	this.status = status;
    }
}
