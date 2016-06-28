package com.lapsa.kkb.core;

public class KKBOrderItem extends KKBBaseEntity<Integer> {
    private static final long serialVersionUID = -7214383627734413167L;
    private static final int PRIME = 7;
    private static final int MULTIPLIER = 7;

    private KKBOrder order;

    private String name;

    private int quantity;

    private double cost;

    @Override
    protected int getPrime() {
	return PRIME;
    }

    @Override
    protected int getMultiplier() {
	return MULTIPLIER;
    }

    // GENERATED

    public KKBOrder getOrder() {
	return order;
    }

    public void setOrder(KKBOrder order) {
	this.order = order;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public int getQuantity() {
	return quantity;
    }

    public void setQuantity(int quantity) {
	this.quantity = quantity;
    }

    public double getCost() {
	return cost;
    }

    public void setCost(double cost) {
	this.cost = cost;
    }
}
