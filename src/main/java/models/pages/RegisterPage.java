package models.pages;

import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage{

    private final WebDriver driver;

    public RegisterPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }
}
