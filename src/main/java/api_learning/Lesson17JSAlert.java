package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import url.Urls;

import java.time.Duration;

public class Lesson17JSAlert implements Urls {

    private static final By jsAlertSel = By.cssSelector("[onclick='jsAlert()']");
    private static final By jsConfirmSel = By.cssSelector("[onclick='jsConfirm()']");
    private static final By jsPromptSel = By.cssSelector("[onclick='jsPrompt()']");
    private static final By resultSel = By.id("result");

    public static void main(String[] args) {

        WebDriver driver = DriverFactory.getChromeDriver();
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Navigate to the test page
            driver.get(BASE_URL.concat(JAVASCRIPT_ALERTS));
            WebElement resultElem = driver.findElement(resultSel);

            // JS Alert
            driver.findElement(jsAlertSel).click();
            Alert jsAlert = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println("JS Alert content: " + jsAlert.getText());
            jsAlert.accept(); // click button OK
            System.out.println(resultElem.getText());
            System.out.println("==========");

            // JS Confirm
            driver.findElement(jsConfirmSel).click();
            Alert jsConfirm = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println("JS Confirm content: " + jsConfirm.getText());
            jsConfirm.dismiss(); // click button Cancel
            resultElem = driver.findElement(resultSel);
            System.out.println(resultElem.getText());
            System.out.println("==========");

            // JS Prompt
            driver.findElement(jsPromptSel).click();
            Alert jsPrompt = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println("JS Prompt content: " + jsPrompt.getText());
            jsPrompt.sendKeys("This is my message!");
            jsPrompt.accept(); // click button OK
            resultElem = driver.findElement(resultSel);
            System.out.println(resultElem.getText());
            System.out.println("==========");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
