package tech.lapsa.epayment.domain;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import tech.lapsa.java.commons.function.MyNumbers;
import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyOptionals;

@MappedSuperclass
public abstract class EntitySuperclass extends Domain {

    private static final long serialVersionUID = 1L;

    protected EntitySuperclass() {
    }

    protected EntitySuperclass(final Integer id) throws IllegalArgumentException {
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

    // updated

    @Version
    @Column(name = "UPDATED", nullable = false)
    private Timestamp updated;

    public Instant getUpdated() {
	return MyObjects.nullOrGet(updated, Timestamp::toInstant);
    }
}
