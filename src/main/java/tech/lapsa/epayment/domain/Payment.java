package tech.lapsa.epayment.domain;

import java.time.Instant;
import java.util.Currency;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tech.lapsa.java.commons.function.MyOptionals;

@Entity
@Table(name = "PAYMENT")
public abstract class Payment extends IntIdEntitySuperclass {

    private static final long serialVersionUID = 1L;

    // constructor

    protected Payment() {
    }

    // created

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    protected Instant created = Instant.now();

    public Instant getCreated() {
	return created;
    }

    // amount

    @Basic
    @Column(name = "AMOUNT")
    protected Double amount;

    public Double getAmount() {
	return amount;
    }

    // currency

    @Basic
    @Column(name = "CURRENCY")
    protected Currency currency;

    public Currency getCurrency() {
	return currency;
    }

    // reference

    @Basic
    @Column(name = "REFERENCE")
    protected String reference;

    public String getReference() {
	return reference;
    }

    // forInvoice

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "payment")
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
