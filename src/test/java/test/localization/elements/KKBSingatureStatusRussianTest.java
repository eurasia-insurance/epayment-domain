package test.localization.elements;

import com.lapsa.kkb.core.KKBSignatureStatus;

import test.localization.ElementsLocalizationTest;

public class KKBSingatureStatusRussianTest extends ElementsLocalizationTest<KKBSignatureStatus> {

    public KKBSingatureStatusRussianTest() {
	super(KKBSignatureStatus.values(), KKBSignatureStatus.class, LOCALE_RUSSIAN);
    }
}
