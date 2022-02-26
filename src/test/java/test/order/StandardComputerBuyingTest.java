package test.order;

import models.components.order.StandardComputerComponent;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.pom.BaseTest;
import test_data.ComputerDataObject;
import test_flow.order.OrderComputerFlow;
import url.Urls;
import utils.data.DataObjectBuilder;

public class StandardComputerBuyingTest extends BaseTest implements Urls {

    @Test(dataProvider = "standardCompsDataSet")
    public void testStandardCompBuying(ComputerDataObject computerDataObject) {
        driver.get(BASE_URL.concat("/build-your-own-computer"));
        OrderComputerFlow<StandardComputerComponent> orderComputerFlow =
                new OrderComputerFlow<>(driver, StandardComputerComponent.class, computerDataObject);
        orderComputerFlow.buildCompSpecAndAddToCart();
    }

    @DataProvider
    public ComputerDataObject[] standardCompsDataSet(){
        String standardCompDataListLocation = "/src/test/resources/test-data/order/StandardComputerDataList.json";
        return DataObjectBuilder.buildDataObjectFrom(standardCompDataListLocation, ComputerDataObject[].class);
    }
}
