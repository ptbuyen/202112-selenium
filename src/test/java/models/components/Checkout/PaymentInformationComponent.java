package models.components.Checkout;

import io.qameta.allure.Step;
import models.Component;
import models.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import test_data.CreditCardType;

@ComponentCssSelector(value = "#opc-payment_info")
public class PaymentInformationComponent extends Component {

    private final static By creditCardDropdownSel = By.id("CreditCardType");
    private final static By cardHolderNameSel = By.id("CardholderName");
    private final static By cardNumberSel = By.id("CardNumber");
    private final static By cardExpiredMonthSel = By.id("ExpireMonth");
    private final static By cardExpiredYearSel = By.id("ExpireYear");
    private final static By cardCodeSel = By.id("CardCode");
    private final static By purchaseNumSel = By.id("PurchaseOrderNumber");
    private final static By continueBtnSel = By.cssSelector(".payment-info-next-step-button");

    public PaymentInformationComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    @Step(value = "Select credit card type as {creditCardType}")
    public void selectCardType(CreditCardType creditCardType){
        Select select = new Select(component.findElement(creditCardDropdownSel));
        switch (creditCardType){
            case VISA:
                select.selectByVisibleText("Visa");
                break;
            case MASTER_CARD:
                select.selectByVisibleText("Master card");
                break;
            case DISCOVER:
                select.selectByVisibleText("Discover");
                break;
            case AMEX:
                select.selectByVisibleText("Amex");
                break;
            default:
                throw new IllegalArgumentException("Credit card type can be null!");
        }
    }

    @Step(value = "Input card holder name as {name}")
    public void inputCardHolderName(String name){
        component.findElement(cardHolderNameSel).sendKeys(name);
    }

    @Step(value = "Input card number as {number}")
    public void inputCardNumber(String number){
        component.findElement(cardNumberSel).sendKeys(number);
    }

    @Step(value = "Input expired month as {month}")
    public void inputExpiredMonth(String month){
        Select select = new Select(component.findElement(cardExpiredMonthSel));
        select.selectByValue(month);
    }

    @Step(value = "Input expired year as {year}")
    public void inputExpiredYear(String year){
        Select select = new Select(component.findElement(cardExpiredYearSel));
        select.selectByVisibleText(year);
    }

    @Step(value = "Input purchase number order as {number}")
    public void inputPurchaseOrderNumber(String number){
        component.findElement(purchaseNumSel).sendKeys(number);
    }

    @Step(value = "Input card code as {code}")
    public void inputCardCode(String code){
        component.findElement(cardCodeSel).sendKeys(code);
    }

    @Step(value = "Click on continue button at Payment Information")
    public void clickOnContinueBtn(){
        WebElement continueBtnElem = component.findElement(continueBtnSel);
        continueBtnElem.click();
        wait.until(ExpectedConditions.invisibilityOf(continueBtnElem));
    }
}
