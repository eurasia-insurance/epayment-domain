package test.localization.elements;

import tech.lapsa.epayment.domain.Localization;
import test.localization.ElementsLocalizationTest;

public class LocalizationRussianTest extends ElementsLocalizationTest<Localization> {

    public LocalizationRussianTest() {
	super(Localization.values(), Localization.class, LOCALE_RUSSIAN);
    }
}
