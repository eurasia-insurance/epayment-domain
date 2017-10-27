package test.localization.domain;

import com.lapsa.kkb.core.DisplayNameElements;

import test.localization.ElementsLocalizationTest;

public class DisplayNameElementsEnglishTest extends ElementsLocalizationTest<DisplayNameElements> {

    public DisplayNameElementsEnglishTest() {
	super(DisplayNameElements.values(), DisplayNameElements.class, LOCALE_ENGLISH);
    }
}
