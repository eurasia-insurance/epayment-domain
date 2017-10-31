package test.localization.elements;

import tech.lapsa.epayment.domain.Localization;
import test.localization.ElementsLocalizationTest;

public class LocalizationEnglishTest extends ElementsLocalizationTest<Localization> {

    public LocalizationEnglishTest() {
	super(Localization.values(), Localization.class, LOCALE_ENGLISH);
    }
}
