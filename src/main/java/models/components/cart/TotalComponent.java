package models.components.cart;

import io.qameta.allure.Step;
import models.Component;
import models.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ComponentCssSelector(value = ".cart-footer .totals")
public class TotalComponent extends Component {

    private final static By priceTableRowSel = By.cssSelector("table tr");
    private final static By priceTypeRowSel = By.cssSelector(".cart-total-left");
    private final static By priceValueRowSel = By.cssSelector(".cart-total-right");
    private final static By tosSel = By.cssSelector("#termsofservice");
    private final static By checkoutBtnSel = By.cssSelector("#checkout");

    public TotalComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    @Step(value = "Verify checkout prices")
    public Map<String, Double> priceCategories(){
        Map<String, Double> priceCategories = new HashMap<>();
        List<WebElement> priceTableRowElems = component.findElements(priceTableRowSel);
        for (WebElement tableRowElem : priceTableRowElems) {
            WebElement priceTypeElem = tableRowElem.findElement(priceTypeRowSel);
            WebElement priceValeElem = tableRowElem.findElement(priceValueRowSel);
            String priceType = priceTypeElem.getText().trim();
            double priceValue = Double.parseDouble(priceValeElem.getText().trim());
            priceCategories.put(priceType, priceValue);
        }
        return priceCategories;
    }

    @Step(value = "Click on Term of service")
    public TotalComponent agreeTOS(){
        component.findElement(tosSel).click();
        return this;
    }

    @Step(value = "Click on Checkout button")
    public void clickOnCheckoutBtn(){
        component.findElement(checkoutBtnSel).click();
    }
}
