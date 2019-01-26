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

import tech.lapsa.java.commons.exceptions.IllegalArgument;
import tech.lapsa.java.commons.exceptions.IllegalState;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.function.MyStrings;

@Entity
@Table(name = "PAYMENT")
public abstract class Payment<T extends Payment<T>> extends EntitySuperclass {

    private static final long serialVersionUID = 2L;

    // created

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    protected Instant created = Instant.now();

    public Instant getCreated() {
	return created;
    }

    // canceled

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CANCELED")
    protected Instant canceled;

    public Instant getCanceled() {
	return canceled;
    }

    public boolean isCanceled() {
	return !isAlive();
    }

    public boolean isAlive() {
	return canceled == null;
    }

    // cancelationReason

    @Basic
    @Column(name = "CANCELATION_REASON")
    protected String cancelationReason;

    public String getCancelationReason() {
	return cancelationReason;
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

    // payerName

    @Basic
    @Column(name = "PAYER_NAME")
    protected String payerName;

    public String getPayerName() {
	return payerName;
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

    /**
     * Отмечает оплату как отмененную
     *
     * @param reason
     *            причина отмены
     *
     * @return счет
     * @throws IllegalState
     *             в случае, если оплата уже отменена
     * @throws IllegalArgument
     *             если причина не указана
     */
    public synchronized T cancel(final String reason) throws IllegalState, IllegalArgument {
	MyStrings.requireNonEmpty(IllegalArgument::new, reason, "reason");
	requireNotCanceled();
	canceled = Instant.now();
	cancelationReason = reason;
	return thizz();
    }

    // method

    private void requireNotCanceled() throws IllegalState {
	MyObjects.requireNullMsg(IllegalState::new, canceled, "Is caneled");
    }

    public abstract PaymentMethod getMethod();

    protected abstract T thizz();

}
