package test.order;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import models.components.order.CheapComputerComponent;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.pom.BaseTest;
import test_data.ComputerDataObject;
import test_flow.order.OrderComputerFlow;
import url.Urls;
import utils.data.DataObjectBuilder;

public class CheapComputerBuyingTest extends BaseTest implements Urls {

    @Description("Buying cheap computer data set")
    @TmsLink("TC-001") @TmsLink("TC-002")
    @Test(dataProvider = "cheapComputerDataSet", description = "Buying Cheap Computer")
    public void testCheapCompBuying(ComputerDataObject computerDataObject) {
        WebDriver driver = getDriver();
        driver.get(BASE_URL.concat("/build-your-cheap-own-computer"));
        OrderComputerFlow<CheapComputerComponent> orderComputerFlow =
                new OrderComputerFlow<>(driver, CheapComputerComponent.class, computerDataObject);
        orderComputerFlow.buildCompSpecAndAddToCart();
    }

    @Issue("JIRA_001")
    @Test
    public void testSomething() {
        Assert.fail("Failed by something");
    }

    @DataProvider
    public ComputerDataObject[] cheapComputerDataSet() {
        String cheapCompDataListLocation = "/src/test/resources/test-data/order/CheapComputerDataList.json";
        return DataObjectBuilder.buildDataObjectFrom(cheapCompDataListLocation, ComputerDataObject[].class);
    }
}
