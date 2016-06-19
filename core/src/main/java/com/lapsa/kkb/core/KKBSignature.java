package com.lapsa.kkb.core;

import java.io.Serializable;
import java.util.Arrays;

public class KKBSignature implements Serializable {
    private static final long serialVersionUID = -7295482069867034544L;

    private byte[] data;
    private byte[] digest;
    private boolean inverted = true;
    private KKBSingatureStatus status = KKBSingatureStatus.UNCHECKED;

    public byte[] getData() {
	return data;
    }

    public void setData(byte[] data) {
	if (!Arrays.equals(this.data, data))
	    status = KKBSingatureStatus.UNCHECKED;
	this.data = data;
    }

    public byte[] getDigest() {
	return digest;
    }

    public void setDigest(byte[] digest) {
	if (!Arrays.equals(this.digest, digest))
	    status = KKBSingatureStatus.UNCHECKED;
	this.digest = digest;
    }

    public boolean isInverted() {
	return inverted;
    }

    public void setInverted(boolean inverted) {
	if (this.inverted != inverted)
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

    public static void main(String[] args) {
	byte[] b1 = new byte[] { 1, 2, 3 };
	byte[] b2 = new byte[] { 1, 2, 3 };

	System.out.println(b1.equals(b2));
	System.out.println();
    }
}
