package models.components.cart;

import io.qameta.allure.Step;
import models.Component;
import models.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@ComponentCssSelector(value = ".customer-blocks .checkout-as-guest-or-register-block")
public class CheckoutAsGuestComponent extends Component {

    private final static By checkoutAsGuestBtnSel = By.cssSelector(".checkout-as-guest-button");

    public CheckoutAsGuestComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    @Step(value = "Click on Checkout as Guest button")
    public void clickOnCheckoutAsGuestBtn(){
        component.findElement(checkoutAsGuestBtnSel).click();
    }
}
