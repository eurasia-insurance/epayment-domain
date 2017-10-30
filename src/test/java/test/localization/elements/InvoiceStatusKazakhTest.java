package test.localization.elements;

import tech.lapsa.epayment.domain.InvoiceStatus;
import test.localization.ElementsLocalizationTest;

public class InvoiceStatusKazakhTest extends ElementsLocalizationTest<InvoiceStatus> {

    public InvoiceStatusKazakhTest() {
	super(InvoiceStatus.values(), InvoiceStatus.class, LOCALE_KAZAKH);
    }
}
