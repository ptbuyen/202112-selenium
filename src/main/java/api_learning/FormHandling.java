package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FormHandling {

    public static void main(String[] args) throws InterruptedException {
        // class method | getChromeDriver | return type: WebDriver
        WebDriver driver = DriverFactory.getChromeDriver();

        try {
            // open target page https://the-internet.herokuapp.com/login
            driver.get("https://the-internet.herokuapp.com/login");

            // find username/password Element by using username/password selector
            // POST /session/{sessionID}/element --- {selector: ...}
            WebElement usernameElem = driver.findElement(By.id("username"));
            WebElement passwordElem = driver.findElement(By.cssSelector("#password"));

            // input username and password
            usernameElem.sendKeys("teo");
            usernameElem.clear();
            usernameElem.sendKeys("tomsmith");
            passwordElem.sendKeys("11559977");
            passwordElem.clear();
            passwordElem.sendKeys("SuperSecretPassword!");

            // click button login
            WebElement clickLoginBtnElem = driver.findElement(By.cssSelector("#login button[type='submit']"));
            clickLoginBtnElem.click();

            Thread.sleep(3000);
        } catch (Exception ignored) {

        } finally {
            // quit driver session
            driver.quit();
        }

    }
}
