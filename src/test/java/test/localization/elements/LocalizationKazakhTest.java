package test.localization.elements;

import tech.lapsa.epayment.domain.Localization;
import test.localization.ElementsLocalizationTest;

public class LocalizationKazakhTest extends ElementsLocalizationTest<Localization> {

    public LocalizationKazakhTest() {
	super(Localization.values(), Localization.class, LOCALE_KAZAKH);
    }
}
