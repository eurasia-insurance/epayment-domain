package tech.lapsa.epayment.domain;

import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyOptionals;

public abstract class AEntity<ID> extends ADomain {

    private static final long serialVersionUID = 1L;

    protected AEntity() {
    }

    protected AEntity(ID id) {
	this.id = MyObjects.requireNonNull(id, "id");
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
