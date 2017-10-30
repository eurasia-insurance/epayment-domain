package test.localization.elements;

import tech.lapsa.epayment.domain.InvoiceStatus;
import test.localization.ElementsLocalizationTest;

public class InvoiceStatusRussianTest extends ElementsLocalizationTest<InvoiceStatus> {

    public InvoiceStatusRussianTest() {
	super(InvoiceStatus.values(), InvoiceStatus.class, LOCALE_RUSSIAN);
    }
}
