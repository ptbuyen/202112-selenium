package test_flow.order;

import models.components.Checkout.BillingAddressComponent;
import models.components.cart.CartItemRowComponent;
import models.components.cart.TotalComponent;
import models.components.order.ComputerEssentialComponent;
import models.pages.CheckoutOptionPage;
import models.pages.CheckoutPage;
import models.pages.ComputerItemDetailsPage;
import models.pages.ShoppingCartPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import test_data.ComputerDataObject;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderComputerFlow<T extends ComputerEssentialComponent> {

    private final WebDriver driver;
    private final Class<T> computerEssentialComponent;
    private final ComputerDataObject computerDataObject;
    private int itemQuantity;
    private double totalItemPrice;

    public OrderComputerFlow(WebDriver driver, Class<T> computerEssentialComponent, ComputerDataObject computerDataObject) {
        this.driver = driver;
        this.computerEssentialComponent = computerEssentialComponent;
        this.computerDataObject = computerDataObject;
    }

    public OrderComputerFlow(WebDriver driver, Class<T> computerEssentialComponent, ComputerDataObject computerDataObject,
                             int itemQuantity) {
        this.driver = driver;
        this.computerEssentialComponent = computerEssentialComponent;
        this.computerDataObject = computerDataObject;
        this.itemQuantity = itemQuantity;
    }

    public double buildCompSpecAndAddToCart() {
        ComputerItemDetailsPage computerItemDetailsPage = new ComputerItemDetailsPage(driver);
        T compEssentialComponent =
                computerItemDetailsPage.computerEssentialComp(computerEssentialComponent);

        // Unselect all default options
        compEssentialComponent.unselectDefaultOptions();

        String processFullStr = compEssentialComponent.selectProcessorType(computerDataObject.getProcessorType());
        double additionalProcessorPrice = extractAdditionalPrice(processFullStr);

        String ramFullStr = compEssentialComponent.selectRAMType(computerDataObject.getRam());
        double additionalRAMPrice = extractAdditionalPrice(ramFullStr);

        String hddFullStr = compEssentialComponent.selectHDD(computerDataObject.getHdd());
        double additionalHDDPrice = extractAdditionalPrice(hddFullStr);
        if (itemQuantity != 0) {
            compEssentialComponent.setProductQuantity(itemQuantity);
        }

        double additionalOSPrice = 0;
        if (computerDataObject.getOs() != null) {
            String osFullStr = compEssentialComponent.selectOS(computerDataObject.getOs());
            additionalOSPrice = extractAdditionalPrice(osFullStr);
        }

        String softwareFullStr = compEssentialComponent.selectSoftware(computerDataObject.getSoftware());
        double additionalSoftwarePrice = extractAdditionalPrice(softwareFullStr);

        double basePrice = compEssentialComponent.productPrice();
        double additionalPrices = additionalProcessorPrice + additionalRAMPrice
                + additionalHDDPrice + additionalOSPrice + additionalSoftwarePrice;

        int currentItemQuantity = itemQuantity == 0 ? 1 : itemQuantity;
        totalItemPrice = (basePrice + additionalPrices) * currentItemQuantity;
        compEssentialComponent.clickOnAddToCartBtn();

        // Wait until the item added to cart
        compEssentialComponent.waitUntilItemAddedToCart();

        // Scroll up the page
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, -document.body.scrollHeight);");

        // Then navigate to shopping cart
        computerItemDetailsPage.headerComp().clickOnShoppingCartLink();
        return totalItemPrice;
    }

    private double extractAdditionalPrice(String itemStr) {
        double price = 0;
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(itemStr);
        if (matcher.find()) {
            price = Double.parseDouble(matcher.group(1).replaceAll("[-+]", ""));
        }
        return price;
    }

    public void verifyShoppingCart(double allItemPrices) {
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        List<CartItemRowComponent> cartItemRowComps = shoppingCartPage.cartItemRowComponents();
        Assert.assertTrue(cartItemRowComps.size() > 0, "[ERR] No item display in shopping cart!");

        double currentSubTotals = 0;
        for (CartItemRowComponent cartItemRowComp : cartItemRowComps) {
            currentSubTotals = currentSubTotals + cartItemRowComp.subTotal();
        }
        Assert.assertEquals(currentSubTotals, allItemPrices, "[ERR] Shopping cart item's subtotal is incorrect!");

        // Verify total summary before Checking out
        Map<String, Double> priceCategories = shoppingCartPage.totalComponent().priceCategories();
        double checkoutSubTotal = 0;
        double checkoutOtherTheFee = 0;
        double checkoutTotal = 0;
        for (String priceType : priceCategories.keySet()) {
            if (priceType.startsWith("Sub-Total")) {
                checkoutSubTotal = priceCategories.get(priceType);
            } else if (priceType.startsWith("Total")) {
                checkoutTotal = priceCategories.get(priceType);
            } else {
                checkoutOtherTheFee = checkoutOtherTheFee + priceCategories.get(priceType);
            }
        }
        Assert.assertEquals(checkoutSubTotal, currentSubTotals, "[ERR] Checkout sub-total is incorrect!");
        Assert.assertEquals(checkoutSubTotal + checkoutOtherTheFee, checkoutTotal, "[ERR] Checkout total is incorrect!");
    }

    public void agreeTosAndCheckoutAsGuest() {
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        shoppingCartPage.totalComponent().agreeTOS().clickOnCheckoutBtn();
        new CheckoutOptionPage(driver).clickOnCheckoutAsGuestBtn();
    }

    public void inputBillingAddress(){
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        BillingAddressComponent billingAddressComp = checkoutPage.billingAddressComp();
        billingAddressComp.selectInputNewAddress();
        billingAddressComp.inputFirstName("firstname");
        billingAddressComp.inputLastName("lastname");
        billingAddressComp.inputEmail("email@sth.com");
        billingAddressComp.selectCountry("United States");
        billingAddressComp.selectState("Alabama");
        billingAddressComp.inputCity("city");
        billingAddressComp.inputAdd1("address 1");
        billingAddressComp.inputZipcode("888");
        billingAddressComp.inputPhoneNo("777-888-999");
        billingAddressComp.clickOnContinueBtn();
    }
}
