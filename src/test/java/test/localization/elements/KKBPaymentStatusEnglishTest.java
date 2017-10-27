package test.localization.elements;

import com.lapsa.kkb.core.KKBPaymentStatus;

import test.localization.ElementsLocalizationTest;

public class KKBPaymentStatusEnglishTest extends ElementsLocalizationTest<KKBPaymentStatus> {

    public KKBPaymentStatusEnglishTest() {
	super(KKBPaymentStatus.values(), KKBPaymentStatus.class, LOCALE_ENGLISH);
    }
}
