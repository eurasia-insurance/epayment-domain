package tech.lapsa.epayment.domain;

import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyOptionals;

public abstract class AEntity<ID> extends ADomain {

    private static final long serialVersionUID = 1L;

    public AEntity() {
	super(13);
    }
    
    protected AEntity(ID id, int prime, int multiplier) {
	super(prime, multiplier);
	this.id = MyObjects.requireNonNull(id, "id");
    }

    protected AEntity(int prime, int multiplier) {
	super(prime, multiplier);
    }

    protected AEntity(int prime) {
	this(prime, prime);
    }

    protected AEntity(ID id, int prime) {
	this(id, prime, prime);
    }

    // id

    protected ID id;

    public ID getId() {
	return id;
    }

    String appendEntityId() {
	return appendEntityId(id);
    }

    static String appendEntityId(Object id) {
	return " [ID=" + MyOptionals.of(id).map(Object::toString).orElse("NONE") + "]";
    }
}
