package test.localization.domain;

import com.lapsa.kkb.core.DisplayNameElements;

import test.localization.ElementsLocalizationTest;

public class DisplayNameElementsRussianTest extends ElementsLocalizationTest<DisplayNameElements> {

    public DisplayNameElementsRussianTest() {
	super(DisplayNameElements.values(), DisplayNameElements.class, LOCALE_RUSSIAN);
    }
}
