package test.localization.elements;

import com.lapsa.kkb.core.KKBSignatureStatus;

import test.localization.ElementsLocalizationTest;

public class KKBSingatureStatusEnglishTest extends ElementsLocalizationTest<KKBSignatureStatus> {

    public KKBSingatureStatusEnglishTest() {
	super(KKBSignatureStatus.values(), KKBSignatureStatus.class, LOCALE_ENGLISH);
    }
}
