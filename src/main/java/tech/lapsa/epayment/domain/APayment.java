package tech.lapsa.epayment.domain;

import java.time.Instant;
import java.util.Optional;

import tech.lapsa.java.commons.function.MyOptionals;

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

    // reference

    protected String reference;

    public String getReference() {
	return reference;
    }

    // forInvoice

    protected Invoice forInvoice;

    public Invoice getForInvoice() {
	return forInvoice;
    }

    public Optional<Invoice> optionalForInvoice() {
	return MyOptionals.of(getForInvoice());
    }

    // method

    public abstract PaymentMethod getMethod();
}
