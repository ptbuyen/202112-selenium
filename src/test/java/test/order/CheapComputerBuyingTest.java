package test.order;

import models.components.order.CheapComputerComponent;
import models.components.order.ComputerEssentialComponent;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.pom.BaseTest;
import test_data.ComputerDataObject;
import test_flow.order.OrderComputerFlow;
import url.Urls;
import utils.data.DataObjectBuilder;

public class CheapComputerBuyingTest extends BaseTest implements Urls {

    @Test(dataProvider = "cheapComputerDataSet")
    public void testCheapCompBuying(ComputerDataObject computerDataObject) {
        driver.get(BASE_URL.concat("/build-your-cheap-own-computer"));
        OrderComputerFlow<CheapComputerComponent> orderComputerFlow =
                new OrderComputerFlow<>(driver, CheapComputerComponent.class, computerDataObject);
        orderComputerFlow.buildCompSpecAndAddToCart();
    }

    @DataProvider
    public ComputerDataObject[] cheapComputerDataSet() {
        String cheapCompDataListLocation = "/src/test/resources/test-data/order/CheapComputerDataList.json";
        return DataObjectBuilder.buildDataObjectFrom(cheapCompDataListLocation, ComputerDataObject[].class);
    }
}
