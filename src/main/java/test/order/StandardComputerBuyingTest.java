package test.order;

import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import models.components.order.StandardComputerComponent;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.BaseTest;
import test_data.ComputerDataObject;
import test_data.CreditCardType;
import test_data.PaymentMethod;
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
        WebDriver driver = getDriver();
        driver.get(BASE_URL.concat("/build-your-own-computer"));
        int itemQuantity = new SecureRandom().nextInt(100) + 1;
        OrderComputerFlow<StandardComputerComponent> orderComputerFlow =
                new OrderComputerFlow<>(driver, StandardComputerComponent.class, computerDataObject, itemQuantity);
        allItemPrices = allItemPrices + orderComputerFlow.buildCompSpecAndAddToCart();
        orderComputerFlow.verifyShoppingCart(allItemPrices);
        orderComputerFlow.agreeTosAndCheckoutAsGuest();
        orderComputerFlow.inputBillingAddress();
        orderComputerFlow.inputShippingAddress();
        orderComputerFlow.selectShippingMethod();
        orderComputerFlow.selectPaymentMethod(PaymentMethod.CREDIT_CARD);
        orderComputerFlow.inputPaymentInfo(CreditCardType.DISCOVER);
        orderComputerFlow.confirmOrder();
        allItemPrices = 0;
    }

    @DataProvider
    public ComputerDataObject[] standardCompsDataSet(){
        String standardCompDataListLocation = "/src/main/resources/test-data/order/StandardComputerDataList.json";
        return DataObjectBuilder.buildDataObjectFrom(standardCompDataListLocation, ComputerDataObject[].class);
    }
}
