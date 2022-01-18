package models.components.order;

import models.Component;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BaseItemDetailsComponent extends Component {

    public BaseItemDetailsComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }
}
