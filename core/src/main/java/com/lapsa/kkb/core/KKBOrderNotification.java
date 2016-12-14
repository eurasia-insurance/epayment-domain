package com.lapsa.kkb.core;

public abstract class KKBOrderNotification extends KKBNotification {
    private static final long serialVersionUID = -5100357344568307829L;

    private KKBOrder order;

    // GENERATED

    public KKBOrder getOrder() {
	return order;
    }

    @Deprecated
    public void setOrder(KKBOrder order) {
	this.order = order;
    }
}
