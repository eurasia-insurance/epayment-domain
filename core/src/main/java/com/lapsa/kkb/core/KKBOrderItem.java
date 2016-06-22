package com.lapsa.kkb.core;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class KKBOrderItem extends BaseEntity<Integer> {
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

    @Override
    public int hashCode() {
	return new HashCodeBuilder(getPrime(), getMultiplier())
		.appendSuper(super.hashCode())
		.append(order)
		.append(name)
		.append(quantity)
		.append(cost)
		.toHashCode();
    }

    @Override
    public boolean equals(Object other) {
	if (other == null || other.getClass() != getClass())
	    return false;
	if (other == this)
	    return true;
	KKBOrderItem that = (KKBOrderItem) other;
	return new EqualsBuilder()
		.appendSuper(super.equals(that))
		.append(order, that.order)
		.append(name, that.name)
		.append(quantity, that.quantity)
		.append(cost, that.cost)
		.isEquals();
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
