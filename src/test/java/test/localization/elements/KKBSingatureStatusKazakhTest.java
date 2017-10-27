package test.localization.elements;

import com.lapsa.kkb.core.KKBSignatureStatus;

import test.localization.ElementsLocalizationTest;

public class KKBSingatureStatusKazakhTest extends ElementsLocalizationTest<KKBSignatureStatus> {

    public KKBSingatureStatusKazakhTest() {
	super(KKBSignatureStatus.values(), KKBSignatureStatus.class, LOCALE_KAZAKH);
    }
}
