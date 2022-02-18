package test.global;

import driver.DriverFactory;
import models.components.global.header.HeaderComponent;
import models.pages.CategoryPage;
import models.pages.HomePage;
import models.pages.RegisterPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import test_flow.global.FooterTestFlow;
import test_flow.global.HeaderTestFlow;
import url.Urls;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class HeaderTest implements Urls {

    @Test
    public void testHomepageHeader() {
        WebDriver driver = DriverFactory.getChromeDriver();
        driver.get(BASE_URL.concat(HOME_PAGE));

        try {
            HeaderTestFlow headerTestFlow = new HeaderTestFlow(driver);
            headerTestFlow.verifyHeaderComponent(HomePage.class);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testRegisterPageHeader() {
        WebDriver driver = DriverFactory.getChromeDriver();
        driver.get(BASE_URL.concat(REGISTER_PAGE));
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//        wait.until(ExpectedConditions.urlToBe(BASE_URL.concat(REGISTER_PAGE)));

        try {
            HeaderTestFlow headerTestFlow = new HeaderTestFlow(driver);
            headerTestFlow.verifyHeaderComponent(RegisterPage.class);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    @Test
    public void testCategoryPageHeader() {
        WebDriver driver = DriverFactory.getChromeDriver();
        List<String> categorySlugs = Arrays.asList("/books","/computers","/electronics");
        String randomSlug = categorySlugs.get(new SecureRandom().nextInt(categorySlugs.size()));
        driver.get(BASE_URL.concat(randomSlug));

        try {
            HeaderTestFlow headerTestFlow = new HeaderTestFlow(driver);
            headerTestFlow.verifyHeaderComponent(CategoryPage.class);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    @Test
    private void testHomepageHeader_() {
        WebDriver driver = DriverFactory.getChromeDriver();
        driver.get(BASE_URL.concat(HOME_PAGE));

        try {
            HomePage homePage = new HomePage(driver);
            HeaderComponent headerComponent = homePage.headerComp();
            System.out.println("All links: " + headerComponent.allLinksNumber());
            headerComponent.searchInputElem().sendKeys("Laptop");
            headerComponent.searchInputBtn().click();
//            Thread.sleep(3000); // debug purpose only
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    @Test
    private void testRegisterPageHeader_() {
        WebDriver driver = DriverFactory.getChromeDriver();
        driver.get(BASE_URL.concat(REGISTER_PAGE));

        try {
            RegisterPage registerPage = new RegisterPage(driver);
            registerPage.headerComp().searchInputElem().sendKeys("Laptop");
            registerPage.headerComp().searchInputBtn().click();
            Thread.sleep(3000); // debug purpose only
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
