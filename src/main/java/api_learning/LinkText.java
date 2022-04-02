package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LinkText {

    public static void main(String[] args) {
        // class method | getChromeDriver | return type: WebDriver
        WebDriver driver = DriverFactory.getChromeDriver();

        try {
            // open target page https://the-internet.herokuapp.com/login
            driver.get("https://the-internet.herokuapp.com/login");

            WebElement powerByLinkTextElem = driver.findElement(By.partialLinkText("Elemental"));
            String linkText = powerByLinkTextElem.getText();
            String linkHref = powerByLinkTextElem.getAttribute("href");
            HyperLink hyperLink = new HyperLink(linkText, linkHref);

            System.out.println(linkText);
            System.out.println(linkHref);
        } catch (Exception ignored) {

        } finally {
            // quit driver session
            driver.quit();
        }
    }

    public static class HyperLink {
        private String text;
        private String link;

        public HyperLink(String text, String link) {
            this.text = text;
            this.link = link;
        }

        public String link() {
            return link;
        }

        public String text() {
            return text;
        }
    }
}
