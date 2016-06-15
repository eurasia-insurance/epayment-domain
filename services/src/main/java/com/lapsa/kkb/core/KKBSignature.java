package com.lapsa.kkb.core;

import java.io.Serializable;

public class KKBSignature implements Serializable {
    private static final long serialVersionUID = -7295482069867034544L;

    private byte[] data;
    private byte[] signature;
    private boolean inverted = true;
    private KKBSingatureStatus status = KKBSingatureStatus.UNCHECKED;

    public byte[] getData() {
	return data;
    }

    public void setData(byte[] data) {
	status = KKBSingatureStatus.UNCHECKED;
	this.data = data;
    }

    public byte[] getSignature() {
	return signature;
    }

    public void setSignature(byte[] signature) {
	status = KKBSingatureStatus.UNCHECKED;
	this.signature = signature;
    }

    public boolean isInverted() {
	return inverted;
    }

    public void setInverted(boolean inverted) {
	status = KKBSingatureStatus.UNCHECKED;
	this.inverted = inverted;
    }

    public KKBSingatureStatus getStatus() {
	return status;
    }

    @Deprecated
    public void setStatus(KKBSingatureStatus status) {
	this.status = status;
    }
}
