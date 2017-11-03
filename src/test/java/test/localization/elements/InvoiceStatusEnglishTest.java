package test.localization.elements;

import tech.lapsa.epayment.domain.InvoiceStatus;
import test.localization.ElementsLocalizationTest;

public class InvoiceStatusEnglishTest extends ElementsLocalizationTest<InvoiceStatus> {

    public InvoiceStatusEnglishTest() {
	super(InvoiceStatus.values(), InvoiceStatus.class, LOCALE_ENGLISH);
    }
}
