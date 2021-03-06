package models.components.global.header;

import io.qameta.allure.Step;
import models.Component;
import models.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@ComponentCssSelector(value = ".header")
public class HeaderComponent extends Component {

    private static final By shoppingCartLinkSel = By.cssSelector("#topcartlink");
    private static final By searchInputSel = By.cssSelector("form input");
    private static final By searchInputBtnSel = By.cssSelector(".search-box-button");
    private static final By allLinksSel = By.tagName("a");

    public HeaderComponent(WebDriver driver, WebElement element) {
        super(driver, element);
        this.driver = driver;
    }

    public LogoComponent logoComp() {
        return findComponent(LogoComponent.class, driver);
    }

    @Step("Click on shopping cart link")
    public void clickOnShoppingCartLink() {
        WebElement shoppingCartLinkElem = findElement(shoppingCartLinkSel);
        scrollUptoElement(shoppingCartLinkElem);
        shoppingCartLinkElem.click();
    }

    public WebElement searchInputElem() {
        return this.component.findElement(searchInputSel);
    }

    public WebElement searchInputBtn() {
        return this.component.findElement(searchInputBtnSel);
    }

    public int allLinksNumber() {
        return this.component.findElements(allLinksSel).size();
    }
}
