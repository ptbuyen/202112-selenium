package models.pages;

import models.components.Checkout.*;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public BillingAddressComponent billingAddressComp() {
        return findComponent(BillingAddressComponent.class, driver);
    }

    public ShippingAddressComponent shippingAddressComponent() {
        return findComponent(ShippingAddressComponent.class, driver);
    }

    public ShippingMethodComponent shippingMethodComponent() {
        return findComponent(ShippingMethodComponent.class, driver);
    }

    public PaymentMethodComponent paymentMethodComponent() {
        return findComponent(PaymentMethodComponent.class, driver);
    }

    public PaymentInformationComponent paymentInformationComponent() {
        return findComponent(PaymentInformationComponent.class, driver);
    }

    ConfirmOrderComponent confirmOrderComponent() {
        return findComponent(ConfirmOrderComponent.class, driver);
    }
}
