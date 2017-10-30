package test.localization.elements;

import tech.lapsa.epayment.domain.PaymentMethod;
import test.localization.ElementsLocalizationTest;

public class PaymentMethodRussianTest extends ElementsLocalizationTest<PaymentMethod> {

    public PaymentMethodRussianTest() {
	super(PaymentMethod.values(), PaymentMethod.class, LOCALE_RUSSIAN);
    }
}
