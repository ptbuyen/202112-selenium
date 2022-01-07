package driver;

import org.apache.commons.exec.OS;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {

    public static WebDriver getChromeDriver(){
        // Location of WebDriver
        String chromeDriverLocation = null;
        String currentProjectLocation = System.getProperty("user.dir");
        chromeDriverLocation = OS.isFamilyMac()
                ? currentProjectLocation.concat("/src/test/resources/drivers/chromedriver")
                : currentProjectLocation.concat("\\src\\test\\resources\\drivers\\chromedriver.exe");

        // Chrome Browser option
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito");

        // Start browser section
        System.setProperty("webdriver.chrome.driver", chromeDriverLocation);

        // Open a webpage
        WebDriver driver = new ChromeDriver(chromeOptions); // Start WebDriver Server | Chrome Driver ---> Chrome Browser
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        return driver;
    }
}
