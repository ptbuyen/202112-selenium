package test.order;

import driver.DriverFactory;
import models.components.order.CheapComputerComponent;
import models.components.order.StandardComputerComponent;
import models.pages.ComputerItemDetailsPage;
import org.openqa.selenium.WebDriver;
import url.Urls;

public class BuyingComputerTest implements Urls {

    public static void main(String[] args) {

        WebDriver driver = DriverFactory.getChromeDriver();
        driver.get(BASE_URL.concat("/build-your-cheap-own-computer"));

        ComputerItemDetailsPage computerItemDetailsPage = new ComputerItemDetailsPage(driver);
        CheapComputerComponent cheapComputerComponent =
                computerItemDetailsPage.computerEssentialComp(CheapComputerComponent.class);

        StandardComputerComponent standardComputerComponent =
                computerItemDetailsPage.computerEssentialComp(StandardComputerComponent.class);

        cheapComputerComponent.selectProcessorType("cheap pro");
        cheapComputerComponent.selectRAMType("cheap ram");

        standardComputerComponent.selectProcessorType("stand pro");
        standardComputerComponent.selectRAMType("stand ram");
    }
}
