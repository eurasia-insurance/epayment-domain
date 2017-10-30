package tech.lapsa.epayment.domain;

import java.time.Instant;

import tech.lapsa.java.commons.localization.Localized;

public abstract class APayment extends AEntity<Integer> {

    private static final long serialVersionUID = 1L;

    public APayment() {
	this(17);
    }

    protected APayment(int prime, int multiplier) {
	super(prime, multiplier);
    }

    protected APayment(int prime) {
	this(prime, prime);
    }

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

    public abstract Localized getMethod();
}
