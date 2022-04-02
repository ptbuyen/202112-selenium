package test.global;

import org.testng.Assert;
import org.testng.annotations.Test;
import url.Urls;

public class FooterTestCopy implements Urls {

    @Test
    public void testHomepageFooterCopy() {
        System.out.println("testHomepageFooter");
    }

    @Test(dependsOnMethods = {"importantMethod"})
    public void anotherTestMethodCopy() {
        System.out.println("anotherTestMethod");
    }

    @Test
    public void importantMethod() {
        Assert.fail();
    }
}
