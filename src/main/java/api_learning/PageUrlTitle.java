package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.WebDriver;

public class PageUrlTitle {

    public static void main(String[] args) {
        // class method | getChromeDriver | return type: WebDriver
        WebDriver driver = DriverFactory.getChromeDriver();

        try {
            // open target page https://the-internet.herokuapp.com/login
            driver.get("https://the-internet.herokuapp.com/login");

            System.out.println(driver.getCurrentUrl());
            System.out.println(driver.getTitle());
        } catch (Exception ignored) {

        } finally {
            // quit driver session
            driver.quit();
        }
    }
}
