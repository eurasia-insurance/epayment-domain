package tech.lapsa.epayment.jpa;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EPAYMENT_VER_4_0")
@Access(AccessType.PROPERTY)
public class EpaymentVersion {

    @Id
    public int getDummy() {
	return 0;
    }

    public void setDummy(int dummy) {
    }
}
