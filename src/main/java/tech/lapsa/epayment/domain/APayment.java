package tech.lapsa.epayment.domain;

import java.time.Instant;

import tech.lapsa.java.commons.localization.Localized;

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

    public abstract Localized getMethod();
}
