package models.components.Checkout;

import io.qameta.allure.Step;
import models.Component;
import models.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@ComponentCssSelector(value = "#opc-payment_method")
public class PaymentMethodComponent extends Component {

    private final static By codSel = By.cssSelector("[value='Payments.CashOnDelivery']");
    private final static By checkMoneyOrderSel = By.cssSelector("[value='Payments.CheckMoneyOrder']");
    private final static By creditCardSel = By.cssSelector("[value='Payments.Manual']");
    private final static By purchaseOrderSel = By.cssSelector("[value='Payments.PurchaseOrder']");
    private final static By continueBtnSel = By.cssSelector(".payment-method-next-step-button");

    public PaymentMethodComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    @Step(value = "Select cash on delivery")
    public void selectCODMethod(){
        component.findElement(codSel).click();
    }

    @Step(value = "Select check money")
    public void selectCheckMoneyMethod(){
        component.findElement(checkMoneyOrderSel).click();
    }

    @Step(value = "Select credit card")
    public void selectCreditCardMethod(){
        component.findElement(creditCardSel).click();
    }

    @Step(value = "Select purchase order")
    public void selectPurchaseOrderMethod(){
        component.findElement(purchaseOrderSel).click();
    }

    @Step(value = "Click on continue button at Payment Method")
    public void clickOnContinueBtn(){
        WebElement continueBtnElem = component.findElement(continueBtnSel);
        continueBtnElem.click();
        wait.until(ExpectedConditions.invisibilityOf(continueBtnElem));
    }
}
