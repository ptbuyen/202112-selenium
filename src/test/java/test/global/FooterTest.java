package test.global;

import driver.DriverFactory;
import models.pages.CategoryPage;
import models.pages.HomePage;
import models.pages.RegisterPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.pom.BaseTest;
import test_flow.global.FooterTestFlow;
import url.Urls;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class FooterTest extends BaseTest implements Urls {

    @Test
    public void testHomepageFooter() {
        driver.get(BASE_URL.concat(HOME_PAGE));
        FooterTestFlow footerTestFlow = new FooterTestFlow(driver);
        footerTestFlow.verifyFooterComponent(HomePage.class);
    }

    @Test
    public void testRegisterPageFooter() {
        driver.get(BASE_URL.concat(REGISTER_PAGE));
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//        wait.until(ExpectedConditions.urlToBe(BASE_URL.concat(REGISTER_PAGE)));
        FooterTestFlow footerTestFlow = new FooterTestFlow(driver);
        footerTestFlow.verifyFooterComponent(RegisterPage.class);
    }

    @Test
    public void testCategoryPageFooter() {
        List<String> categorySlugs = Arrays.asList("/books", "/computers", "/electronics");
        String randomSlug = categorySlugs.get(new SecureRandom().nextInt(categorySlugs.size()));
        driver.get(BASE_URL.concat(randomSlug));
        Assert.fail();
        FooterTestFlow footerTestFlow = new FooterTestFlow(driver);
        footerTestFlow.verifyFooterComponent(CategoryPage.class);
    }
}
