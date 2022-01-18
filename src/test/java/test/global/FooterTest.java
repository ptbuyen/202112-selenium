package test.global;

import driver.DriverFactory;
import models.components.global.footer.*;
import models.components.global.header.HeaderComponent;
import models.pages.HomePage;
import org.openqa.selenium.WebDriver;
import url.Urls;

public class FooterTest implements Urls {

    public static void main(String[] args) {
        testHomepageFooter();
    }

    private static void testHomepageFooter() {
        WebDriver driver = DriverFactory.getChromeDriver();
        driver.get(BASE_URL.concat(HOME_PAGE));

        try {
            HomePage homePage = new HomePage(driver);
            FooterComponent footerComponent = homePage.footerComponent();
            InformationColumnComponent informationColumnComp = footerComponent.informationColumnComponent();
            CustomerServiceColumnComponent customerServiceColumnComp = footerComponent.customerServiceColumnComponent();
            AccountColumnComponent accountColumnComp = footerComponent.accountColumnComponent();
            FooterColumnComponent followUsColumnComp = footerComponent.followUsColumnComponent();

            GenericTestFlow genericTestFlow = new GenericTestFlow(driver);
            genericTestFlow.testFooterColumn(informationColumnComp);
            genericTestFlow.testFooterColumn(customerServiceColumnComp);
            genericTestFlow.testFooterColumn(accountColumnComp);
            genericTestFlow.testFooterColumn(followUsColumnComp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
