package test.order;

import driver.DriverFactory;
import models.components.order.CheapComputerComponent;
import models.components.order.StandardComputerComponent;
import models.pages.ComputerItemDetailsPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import test.pom.BaseTest;
import url.Urls;

public class BuyingComputerTest extends BaseTest implements Urls {

//    public static void main(String[] args) {
//
//        WebDriver driver = DriverFactory.getChromeDriver();
//        driver.get(BASE_URL.concat("/build-your-cheap-own-computer"));
//
//        ComputerItemDetailsPage computerItemDetailsPage = new ComputerItemDetailsPage(driver);
//        CheapComputerComponent cheapComputerComponent =
//                computerItemDetailsPage.computerEssentialComp(CheapComputerComponent.class);
//
//        StandardComputerComponent standardComputerComponent =
//                computerItemDetailsPage.computerEssentialComp(StandardComputerComponent.class);
//
//        cheapComputerComponent.selectProcessorType("cheap pro");
//        cheapComputerComponent.selectRAMType("cheap ram");
//
//        standardComputerComponent.selectProcessorType("stand pro");
//        standardComputerComponent.selectRAMType("stand ram");
//    }

    @Test
    public void testCheapCompBuying() throws InterruptedException {

        driver.get(BASE_URL.concat("/build-your-cheap-own-computer"));

        ComputerItemDetailsPage computerItemDetailsPage = new ComputerItemDetailsPage(driver);

        CheapComputerComponent cheapComputerComponent =
                computerItemDetailsPage.computerEssentialComp(CheapComputerComponent.class);
        cheapComputerComponent.selectProcessorType("Fast");
        Thread.sleep(2000); // Debug only
        cheapComputerComponent.selectRAMType("8 GB");
        Thread.sleep(2000);

        System.out.println(cheapComputerComponent.productPrice());
        cheapComputerComponent.setProductQuantity(55);
        Thread.sleep(2000);
        cheapComputerComponent.clickOnAddToCartBtn();

    }

    @Test
    public void testStandardCompBuying() throws InterruptedException {

        driver.get(BASE_URL.concat("/build-your-own-computer"));

        ComputerItemDetailsPage computerItemDetailsPage = new ComputerItemDetailsPage(driver);

        StandardComputerComponent standardComputerComponent =
                computerItemDetailsPage.computerEssentialComp(StandardComputerComponent.class);
        standardComputerComponent.selectProcessorType("2.2GHz");
        Thread.sleep(2000); // Debug only
        standardComputerComponent.selectRAMType("8GB");
        Thread.sleep(2000);

        System.out.println(standardComputerComponent.productPrice());
        standardComputerComponent.setProductQuantity(55);
        Thread.sleep(2000);
        standardComputerComponent.clickOnAddToCartBtn();

    }
}
