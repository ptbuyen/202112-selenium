package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import url.Urls;

import java.util.List;

public class DynamicControl implements Urls {

    public static void main(String[] args) {

        WebDriver driver = DriverFactory.getChromeDriver();

        try {
            driver.get(BASE_URL.concat(DYNAMIC_CONTROL_PAGE));

            // Get list of button on the page
            List<WebElement> btnElems = driver.findElements(By.cssSelector("button"));
            final int REMOVE_SECTION_BTN_INDEX = 0; // avoid magic number
            final int ENABLE_SECTION_BTN_INDEX = 1;

            btnElems.get(REMOVE_SECTION_BTN_INDEX).click();
            btnElems.get(ENABLE_SECTION_BTN_INDEX).click();
            System.out.println(btnElems.size());

            // How to check an element is not on that page
            List<WebElement> somethingElems = driver.findElements(By.className("something"));
            System.out.println(somethingElems.isEmpty());

            // False positive | Try to remember "check isEmpty" when using List
            if (somethingElems.isEmpty())
                throw new RuntimeException("The list X is empty");
            
            for (WebElement somethingElem : somethingElems) {
                // Verification points related to somethingElem
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
