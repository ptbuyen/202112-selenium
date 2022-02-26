package test_flow.order;

import models.components.order.ComputerEssentialComponent;
import models.pages.ComputerItemDetailsPage;
import org.openqa.selenium.WebDriver;
import test_data.ComputerDataObject;

public class OrderComputerFlow<T extends ComputerEssentialComponent> {

    private final WebDriver driver;
    private final Class<T> computerEssentialComponent;
    private final ComputerDataObject computerDataObject;

    public OrderComputerFlow(WebDriver driver, Class<T> computerEssentialComponent, ComputerDataObject computerDataObject) {
        this.driver = driver;
        this.computerEssentialComponent = computerEssentialComponent;
        this.computerDataObject = computerDataObject;
    }

    public void buildCompSpecAndAddToCart() {
        ComputerItemDetailsPage computerItemDetailsPage = new ComputerItemDetailsPage(driver);
        T compEssentialComponent =
                computerItemDetailsPage.computerEssentialComp(computerEssentialComponent);
        compEssentialComponent.selectProcessorType(computerDataObject.getProcessorType());
        compEssentialComponent.selectRAMType(computerDataObject.getRam());
        System.out.println(compEssentialComponent.productPrice());
        compEssentialComponent.setProductQuantity(55);
        compEssentialComponent.clickOnAddToCartBtn();
    }
}
