package test_flow.order;

import models.components.order.ComputerEssentialComponent;
import models.pages.ComputerItemDetailsPage;
import org.openqa.selenium.JavascriptExecutor;
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
        compEssentialComponent.selectHDD(computerDataObject.getHdd());
        compEssentialComponent.setProductQuantity(55);
        if (computerDataObject.getOs() != null) {
            compEssentialComponent.selectOS(computerDataObject.getOs());
        }

        compEssentialComponent.clickOnAddToCartBtn();

        // Wait until the item added to cart
        compEssentialComponent.waitUntilItemAddedToCart();

        // Scroll up the page
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, -document.body.scrollHeight);");

        // Then navigate to shopping cart
        computerItemDetailsPage.headerComp().clickOnShoppingCartLink();
    }
}
