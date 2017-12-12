package tech.lapsa.epayment.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import tech.lapsa.java.commons.function.MyNumbers;
import tech.lapsa.java.commons.function.MyOptionals;

@MappedSuperclass
public abstract class BaseEntity extends Domain {

    private static final long serialVersionUID = 1L;

    protected BaseEntity() {
    }

    protected BaseEntity(final Integer id) {
	this.id = MyNumbers.requireNonZero(id, "id");
    }

    // id

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    public Integer getId() {
	return id;
    }

    protected String appendEntityId() {
	return appendEntityId(id);
    }

    protected static String appendEntityId(final Object id) {
	return " [ID=" + MyOptionals.of(id).map(Object::toString).orElse("NONE") + "]";
    }
}
