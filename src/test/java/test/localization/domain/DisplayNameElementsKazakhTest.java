package test.localization.domain;

import com.lapsa.kkb.core.DisplayNameElements;

import test.localization.ElementsLocalizationTest;

public class DisplayNameElementsKazakhTest extends ElementsLocalizationTest<DisplayNameElements> {

    public DisplayNameElementsKazakhTest() {
	super(DisplayNameElements.values(), DisplayNameElements.class, LOCALE_KAZAKH);
    }
}
