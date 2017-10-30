package test.localization.elements;

import tech.lapsa.epayment.domain.PaymentMethod;
import test.localization.ElementsLocalizationTest;

public class PaymentMethodKazakhTest extends ElementsLocalizationTest<PaymentMethod> {

    public PaymentMethodKazakhTest() {
	super(PaymentMethod.values(), PaymentMethod.class, LOCALE_KAZAKH);
    }
}
