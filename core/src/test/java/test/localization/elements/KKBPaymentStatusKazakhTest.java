package test.localization.elements;

import com.lapsa.kkb.core.KKBPaymentStatus;

import test.localization.ElementsLocalizationTest;

public class KKBPaymentStatusKazakhTest extends ElementsLocalizationTest<KKBPaymentStatus> {

    public KKBPaymentStatusKazakhTest() {
	super(KKBPaymentStatus.values(), KKBPaymentStatus.class, LOCALE_KAZAKH);
    }
}
