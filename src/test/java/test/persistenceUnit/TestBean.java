package test.persistenceUnit;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Startup
@Singleton
public class TestBean {

    @PersistenceContext(unitName = EpaymentConstants.PERSISTENCE_UNIT_NAME)
    protected EntityManager em;

    public EntityManager getEm() {
	return em;
    }
}
