package test.localization.elements;

import com.lapsa.kkb.core.KKBPaymentStatus;

import test.localization.ElementsLocalizationTest;

public class KKBPaymentStatusRussianTest extends ElementsLocalizationTest<KKBPaymentStatus> {

    public KKBPaymentStatusRussianTest() {
	super(KKBPaymentStatus.values(), KKBPaymentStatus.class, LOCALE_RUSSIAN);
    }
}
