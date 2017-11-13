package tech.lapsa.epayment.domain;

import java.time.Instant;

public abstract class APayment extends AEntity {

    private static final long serialVersionUID = 1L;

    // created

    protected Instant created = Instant.now();

    public Instant getCreated() {
	return created;
    }

    // amount

    protected Double amount;

    public Double getAmount() {
	return amount;
    }

    // forInvoice

    protected Invoice forInvoice;

    public Invoice getForInvoice() {
	return forInvoice;
    }

    // method

    public abstract PaymentMethod getMethod();

    // controllers

    @Override
    public void unlazy() {
    }
}
