package test_flow.global;

import models.components.global.footer.*;
import models.pages.BasePage;
import models.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import url.Urls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FooterTestFlow implements Urls {

    private WebDriver driver;

    public FooterTestFlow(WebDriver driver) {
        this.driver = driver;
    }

    public void verifyFooterComponent(Class<? extends BasePage> pageClass) {
        BasePage page = null;
        try {
            page = pageClass.getConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FooterComponent footerComponent = page.footerComponent();
        verifyInformationColumn(footerComponent);

        // JUST AN EXAMPLE to handle different flow on pages
        if (page instanceof HomePage){
            System.out.println("Home Page | verifyCustomerColumn....");
            verifyCustomerColumn(footerComponent);
        }
        verifyAccountColumn(footerComponent);
        verifyFollowUsColumn(footerComponent);
//        GenericTestFlow genericTestFlow = new GenericTestFlow(driver);
//        genericTestFlow.testFooterColumn(informationColumnComp);
//        genericTestFlow.testFooterColumn(customerServiceColumnComp);
//        genericTestFlow.testFooterColumn(accountColumnComp);
//        genericTestFlow.testFooterColumn(followUsColumnComp);
    }

    private void verifyInformationColumn(FooterComponent footerComponent) {
        InformationColumnComponent informationColumnComp = footerComponent.informationColumnComponent();
        String actualColumnTitle = informationColumnComp.headerElem().getText().trim();
        String expectedColumnTitle = "INFORMATION";
        Assert.assertEquals(actualColumnTitle, expectedColumnTitle, "[ERR] Column Title is incorrect!");

        List<String> expectedLinkTexts = Arrays.asList("Sitemap", "Shipping & Returns",
                "Privacy Notice", "Conditions of Use", "About us", "Contact us");
        List<String> expectedLinkHrefs = Arrays.asList("/sitemap", "/shipping-returns",
                "/privacy-policy", "/conditions-of-use", "/about-us", "/contactus");
        verifyColumnData(informationColumnComp, expectedLinkTexts, expectedLinkHrefs);
    }

    private void verifyCustomerColumn(FooterComponent footerComponent) {
        CustomerServiceColumnComponent customerServiceColumnComp = footerComponent.customerServiceColumnComponent();
        String actualColumnTitle = customerServiceColumnComp.headerElem().getText().trim();
        String expectedColumnTitle = "CUSTOMER SERVICE";
        Assert.assertEquals(actualColumnTitle, expectedColumnTitle, "[ERR] Column Title is incorrect!");

        List<String> expectedLinkTexts = Arrays.asList("Search", "News",
                "Blog", "Recently viewed products", "Compare products list", "New products");
        List<String> expectedLinkHrefs = Arrays.asList("/search", "/news",
                "/blog", "/recentlyviewedproducts", "/compareproducts", "/newproducts");
        verifyColumnData(customerServiceColumnComp, expectedLinkTexts, expectedLinkHrefs);
    }

    private void verifyAccountColumn(FooterComponent footerComponent) {
        AccountColumnComponent accountColumnComponent = footerComponent.accountColumnComponent();
        String actualColumnTitle = accountColumnComponent.headerElem().getText().trim();
        String expectedColumnTitle = "MY ACCOUNT";
        Assert.assertEquals(actualColumnTitle, expectedColumnTitle, "[ERR] Column Title is incorrect!");

        List<String> expectedLinkTexts = Arrays.asList("My account", "Orders",
                "Addresses", "Shopping cart", "Wishlist");
        List<String> expectedLinkHrefs = Arrays.asList("/customer/info", "/customer/orders",
                "/customer/addresses", "/cart", "/wishlist");
        verifyColumnData(accountColumnComponent, expectedLinkTexts, expectedLinkHrefs);
    }

    private void verifyFollowUsColumn(FooterComponent footerComponent) {
        FollowUsColumnComponent followUsColumnComponent = footerComponent.followUsColumnComponent();
        String actualColumnTitle = followUsColumnComponent.headerElem().getText().trim();
        String expectedColumnTitle = "FOLLOW US";
        Assert.assertEquals(actualColumnTitle, expectedColumnTitle, "[ERR] Column Title is incorrect!");

        List<String> expectedLinkTexts = Arrays.asList("Facebook", "Twitter",
                "RSS", "YouTube", "Google+");
        List<String> expectedLinkHrefs = Arrays.asList("http://www.facebook.com/nopCommerce", "https://twitter.com/nopCommerce",
                "/news/rss/1", "http://www.youtube.com/user/nopCommerce", "https://plus.google.com/+nopcommerce");
        verifyColumnData(followUsColumnComponent, expectedLinkTexts, expectedLinkHrefs);
    }

    private void verifyColumnData(FooterColumnComponent footerColumnComp, List<String> expectedLinkTexts, List<String> expectedLinkHrefs) {
        List<String> actualLinkTexts = new ArrayList<>();
        List<String> actualLinkHrefs = new ArrayList<>();

        for (WebElement link : footerColumnComp.linkElems()) {
            actualLinkTexts.add(link.getText().trim());
            actualLinkHrefs.add(link.getAttribute("href"));
        }

        if (actualLinkTexts.isEmpty() || actualLinkHrefs.isEmpty()) {
            Assert.fail("[ERR] Texts or hyperlinks is empty");
        }

        // SIZE is equal, each element inside is EQUAL
        // Link text verification
        Assert.assertTrue(actualLinkTexts.size() == expectedLinkTexts.size(),
                "[ERR] Text in footer column is incorrect!");
        for (String actualLinkText : actualLinkTexts) {
            System.out.println(actualLinkText);
            Assert.assertTrue(expectedLinkTexts.contains(actualLinkText),
                    "[ERR] " + actualLinkText + " is incorrect value!");
        }

        // Link href verification
        Assert.assertTrue(actualLinkHrefs.size() == expectedLinkHrefs.size(),
                "[ERR] Hyperlink in footer column is incorrect!");
        for (String actualLinkHref : actualLinkHrefs) {
            System.out.println(actualLinkHref);
            if (actualLinkHref.contains(BASE_URL))
                Assert.assertTrue(expectedLinkHrefs.contains(actualLinkHref.substring(BASE_URL.length())),
                    "[ERR] " + actualLinkHref + " is incorrect value!");
            else
                Assert.assertTrue(expectedLinkHrefs.contains(actualLinkHref),
                        "[ERR] " + actualLinkHref + " is incorrect value!");
        }
    }
}
