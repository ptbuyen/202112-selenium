package test.global;

import models.components.global.footer.FooterColumnComponent;
import org.openqa.selenium.WebDriver;

public class GenericTestFlow {

    private WebDriver driver;

    public GenericTestFlow(WebDriver driver) {
        this.driver = driver;
    }

    public void testFooterColumn(FooterColumnComponent footerColumnComponent) {
        System.out.println(footerColumnComponent.headerElem().getText());
        footerColumnComponent.linkElems().forEach(linkElem -> {
//            System.out.print(linkElem.getText() + " | ");
//            System.out.println(linkElem.getAttribute("href"));
        });
    }
}
