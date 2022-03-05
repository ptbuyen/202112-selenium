package test.order;

import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import models.components.order.StandardComputerComponent;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.pom.BaseTest;
import test_data.ComputerDataObject;
import test_flow.order.OrderComputerFlow;
import url.Urls;
import utils.data.DataObjectBuilder;

import java.security.SecureRandom;

public class StandardComputerBuyingTest extends BaseTest implements Urls {

    private double allItemPrices = 0;

    @Description("Buying standard computer data set")
    @TmsLink("TC-002") @TmsLink("TC-003") @TmsLink("TC-004")
    @Test(dataProvider = "standardCompsDataSet", description = "Buying Standard Computer")
    public void testStandardCompBuying(ComputerDataObject computerDataObject) {
        System.out.println("before All item price: " + allItemPrices);
        WebDriver driver = getDriver();
        driver.get(BASE_URL.concat("/build-your-own-computer"));
        int itemQuantity = new SecureRandom().nextInt(100) + 1;
        OrderComputerFlow<StandardComputerComponent> orderComputerFlow =
                new OrderComputerFlow<>(driver, StandardComputerComponent.class, computerDataObject, itemQuantity);
        allItemPrices = allItemPrices + orderComputerFlow.buildCompSpecAndAddToCart();
        System.out.println("after All item price: " + allItemPrices);
        orderComputerFlow.verifyShoppingCart(allItemPrices);
    }

    @DataProvider
    public ComputerDataObject[] standardCompsDataSet(){
        String standardCompDataListLocation = "/src/test/resources/test-data/order/StandardComputerDataList.json";
        return DataObjectBuilder.buildDataObjectFrom(standardCompDataListLocation, ComputerDataObject[].class);
    }
}
