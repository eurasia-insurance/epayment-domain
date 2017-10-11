package com.lapsa.kkb.core;

import static com.lapsa.kkb.core.DisplayNameElements.*;

import java.util.Locale;
import java.util.StringJoiner;
import java.util.function.Function;

import tech.lapsa.java.commons.function.MyOptionals;

public class KKBOrderItem extends KKBBaseEntity<Integer> {
    private static final long serialVersionUID = -7214383627734413167L;
    private static final int PRIME = 7;
    private static final int MULTIPLIER = 7;

    @Override
    protected int getPrime() {
	return PRIME;
    }

    @Override
    protected int getMultiplier() {
	return MULTIPLIER;
    }

    protected KKBOrder order;

    protected String name;

    protected int quantity;

    protected double cost;

    @Override
    public String displayName(DisplayNameVariant variant, Locale locale) {
	StringBuilder sb = new StringBuilder();

	sb.append(ORDER_ITEM.displayName(variant, locale));

	StringJoiner sj = new StringJoiner(", ", " ", "");
	sj.setEmptyValue("");

	MyOptionals.of(name) //
		.map(ORDER_ITEM_NAME.fieldAsCaptionMapper(variant, locale)) //
		.ifPresent(sj::add);

	MyOptionals.of(quantity) //
		.map(Function.identity() //
			.andThen(String::valueOf) //
			.andThen(ORDER_ITEM_QUANTITY.fieldAsCaptionMapper(variant, locale)))
		.ifPresent(sj::add);

	return sb.append(sj.toString()) //
		.append(appendEntityId()) //
		.toString();
    }

    public double getTotal() {
	return cost * quantity;
    }

    // GENERATED

    public KKBOrder getOrder() {
	return order;
    }

    protected void setOrder(KKBOrder order) {
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
	if (order != null)
	    order.calculateTotalAmount();
    }
}
