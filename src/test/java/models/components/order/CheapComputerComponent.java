package models.components.order;

import io.qameta.allure.Step;
import models.ComponentCssSelector;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@ComponentCssSelector(".product-essential")
public class CheapComputerComponent extends ComputerEssentialComponent {

    public CheapComputerComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    @Step("Select processor type as {type}")
    @Override
    public void selectProcessorType(String type) {
        selectCompSpecOption(type);
    }

    @Step("Select RAM type as {type}")
    @Override
    public void selectRAMType(String type) {
        selectCompSpecOption(type);
    }

}
