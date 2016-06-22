package com.lapsa.kkb.core;

import java.util.Arrays;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class KKBSignedData extends BaseDomain {
    private static final long serialVersionUID = -7295482069867034544L;
    private static final int PRIME = 17;
    private static final int MULTIPLIER = 17;

    private byte[] data;
    private byte[] digest;
    private boolean inverted = true;
    private KKBSingatureStatus status = KKBSingatureStatus.UNCHECKED;

    @Override
    protected int getPrime() {
	return PRIME;
    }

    @Override
    protected int getMultiplier() {
	return MULTIPLIER;
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder(getPrime(), getMultiplier())
		.append(data)
		.append(digest)
		.append(inverted)
		.append(status)
		.toHashCode();
    }

    @Override
    public boolean equals(Object other) {
	if (other == null || other.getClass() != getClass())
	    return false;
	if (other == this)
	    return true;
	KKBSignedData that = (KKBSignedData) other;
	return new EqualsBuilder()
		.append(data, that.data)
		.append(digest, that.digest)
		.append(inverted, that.inverted)
		.append(status, that.status)
		.isEquals();
    }

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
}
