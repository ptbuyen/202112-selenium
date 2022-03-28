package models.pages;

import models.components.cart.CheckoutAsGuestComponent;
import org.openqa.selenium.WebDriver;

public class CheckoutOptionPage extends BasePage {

    public CheckoutOptionPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutAsGuestComponent checkoutAsGuestComponent(){
        return findComponent(CheckoutAsGuestComponent.class, driver);
    }
}
