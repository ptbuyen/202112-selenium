package test_flow.order;

import models.components.Checkout.BillingAddressComponent;
import models.components.Checkout.PaymentInformationComponent;
import models.components.Checkout.PaymentMethodComponent;
import models.components.cart.CartItemRowComponent;
import models.components.order.ComputerEssentialComponent;
import models.pages.CheckoutOptionPage;
import models.pages.CheckoutPage;
import models.pages.ComputerItemDetailsPage;
import models.pages.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import test_data.ComputerDataObject;
import test_data.CreditCardType;
import test_data.PaymentMethod;
import test_data.UserDataObject;
import utils.data.DataObjectBuilder;

import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderComputerFlow<T extends ComputerEssentialComponent> {

    private final WebDriver driver;
    private final Class<T> computerEssentialComponent;
    private final ComputerDataObject computerDataObject;
    private int itemQuantity;
    private double totalItemPrice;
    private PaymentMethod paymentMethod = PaymentMethod.COD;
    private CreditCardType creditCardType;
    private UserDataObject defaultCheckoutUser;

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

        CheckoutOptionPage checkoutOptionPage = new CheckoutOptionPage(driver);
        checkoutOptionPage.checkoutAsGuestComponent().clickOnCheckoutAsGuestBtn();
    }

    public void inputBillingAddress() {
        String defaultCheckoutUserJSONLoc = "/src/main/resources/test-data/DefaultCheckoutUser.json";
        defaultCheckoutUser = DataObjectBuilder.buildDataObjectFrom(defaultCheckoutUserJSONLoc, UserDataObject.class);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        BillingAddressComponent billingAddressComp = checkoutPage.billingAddressComp();
        billingAddressComp.selectInputNewAddress();
        billingAddressComp.inputFirstName(defaultCheckoutUser.getFirstname());
        billingAddressComp.inputLastName(defaultCheckoutUser.getLastname());
        billingAddressComp.inputEmail(defaultCheckoutUser.getEmail());
        billingAddressComp.selectCountry(defaultCheckoutUser.getCountry());
        billingAddressComp.selectState(defaultCheckoutUser.getState());
        billingAddressComp.inputCity(defaultCheckoutUser.getCity());
        billingAddressComp.inputAdd1(defaultCheckoutUser.getAdd1());
        billingAddressComp.inputZipcode(defaultCheckoutUser.getZipCode());
        billingAddressComp.inputPhoneNo(defaultCheckoutUser.getPhoneNum());
        billingAddressComp.clickOnContinueBtn();
    }

    public void inputShippingAddress() {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.shippingAddressComponent().clickOnContinueBtn();
    }

    public void selectShippingMethod() {
        List<String> shippingMethod = Arrays.asList("Ground", "Next Day Air", "2nd Day Air");
        String randomShippingMethod = shippingMethod.get(new SecureRandom().nextInt(shippingMethod.size()));
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.shippingMethodComponent().selectShippingMethod(randomShippingMethod);
        checkoutPage.shippingMethodComponent().clickOnContinueBtn();
    }

    public void selectPaymentMethod() {
        this.paymentMethod = PaymentMethod.COD;
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.paymentMethodComponent().selectCODMethod();
        checkoutPage.paymentMethodComponent().clickOnContinueBtn();
    }

    public void selectPaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod == null)
            throw new IllegalArgumentException("Payment method can't be null!");

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        PaymentMethodComponent paymentMethodComponent = checkoutPage.paymentMethodComponent();
        switch (paymentMethod) {
            case CHECK_MONEY_ORDER:
                paymentMethodComponent.selectCheckMoneyMethod();
                this.paymentMethod = PaymentMethod.CHECK_MONEY_ORDER;
                break;
            case CREDIT_CARD:
                paymentMethodComponent.selectCreditCardMethod();
                this.paymentMethod = PaymentMethod.CREDIT_CARD;
                break;
            case PURCHASE_ORDER:
                paymentMethodComponent.selectPurchaseOrderMethod();
                this.paymentMethod = PaymentMethod.PURCHASE_ORDER;
                break;
            default:
                paymentMethodComponent.selectCODMethod();
        }

        checkoutPage.paymentMethodComponent().clickOnContinueBtn();
    }

    // Test card number: https://www.paypalobjects.com/en_GB/vhelp/paypalmanager_help/credit_card_numbers.htm
    public void inputPaymentInfo(CreditCardType creditCardType) {
        if (creditCardType == null) {
            throw new IllegalArgumentException("Credit card type can't be null");
        } else {
            this.creditCardType = creditCardType;
        }

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        PaymentInformationComponent paymentInformationComponent = checkoutPage.paymentInformationComponent();

        if (paymentMethod.equals(PaymentMethod.PURCHASE_ORDER)) {
            // This can be dynamic as well
            paymentInformationComponent.inputPurchaseOrderNumber("123");
        }

        if (paymentMethod.equals(PaymentMethod.CREDIT_CARD)) {
            paymentInformationComponent.selectCardType(creditCardType);
            paymentInformationComponent.inputCardHolderName(
                    defaultCheckoutUser.getFirstname() + " " + defaultCheckoutUser.getLastname());
            String cardNumber = creditCardType.equals(CreditCardType.VISA) ? "4012888888881881" : "6011111111111117";
            paymentInformationComponent.inputCardNumber(cardNumber);

            // Select current month and next year
            Calendar calendar = new GregorianCalendar();
            paymentInformationComponent.inputExpiredMonth(String.valueOf(calendar.get(Calendar.MONTH) + 1));
            paymentInformationComponent.inputExpiredYear(String.valueOf(calendar.get(Calendar.YEAR) + 1));

            paymentInformationComponent.inputCardCode("123");
        }

        paymentInformationComponent.clickOnContinueBtn();
    }

    public void confirmOrder(){
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.confirmOrderComponent().clickOnContinueBtn();
    }
}
