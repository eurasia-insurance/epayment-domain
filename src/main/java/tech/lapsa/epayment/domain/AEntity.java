package tech.lapsa.epayment.domain;

import tech.lapsa.java.commons.function.MyNumbers;
import tech.lapsa.java.commons.function.MyOptionals;

public abstract class AEntity extends ADomain {

    private static final long serialVersionUID = 1L;

    protected AEntity() {
    }

    protected AEntity(Integer id) {
	this.id = MyNumbers.requireNonZero(id, "id");
    }

    // id

    protected Integer id;

    public Integer getId() {
	return id;
    }

    String appendEntityId() {
	return appendEntityId(id);
    }

    static String appendEntityId(Object id) {
	return " [ID=" + MyOptionals.of(id).map(Object::toString).orElse("NONE") + "]";
    }
}
