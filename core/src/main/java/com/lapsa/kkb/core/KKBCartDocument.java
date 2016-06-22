package com.lapsa.kkb.core;

public class KKBCartDocument extends KKBDocument {
    private static final long serialVersionUID = 3602329001760876393L;
    private static final int PRIME = 3;
    private static final int MULTIPLIER = 3;

    @Override
    protected int getPrime() {
	return PRIME;
    }

    @Override
    protected int getMultiplier() {
	return MULTIPLIER;
    }

    // GENERATED

    public KKBCartDocument() {
	super();
    }

    public KKBCartDocument(String content) {
	super(content);
    }
}
