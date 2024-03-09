package ntudp.atvd.lab2;

import org.junit.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class OwnTesting {
    private WebDriver foxDriver;
    private final String url = "https://rozetka.com.ua/ua/";

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-fullscreen");
        options.setImplicitWaitTimeout(Duration.ofSeconds(15));
        foxDriver = new FirefoxDriver();
    }

    @BeforeMethod
    public void precondition() {
        foxDriver.get(url);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        foxDriver.quit();
    }

    @Test
    public void testClickOnCatalog() {
        WebElement catalogButton = foxDriver.findElement(By.id("fat-menu"));
        WebElement menu = foxDriver.findElement(By.xpath("//div[@cdktrapfocus]"));
        Assert.assertNotNull(catalogButton);
        Assert.assertNotNull(menu);

        catalogButton.click();
        Assert.assertFalse(menu.getAttribute("style").contains("display: none"));
    }

    @Test
    public void testSearchField() throws InterruptedException {
        String inputValue = "Смартфон";
        WebElement searchField = foxDriver.findElement(By.tagName("input"));
        Assert.assertNotNull(searchField);

        System.out.println("Attribute name: " + searchField.getAttribute("name"));
        System.out.println("Attribute type: " + searchField.getAttribute("type"));
        System.out.println("Attribute placeholder: " + searchField.getAttribute("placeholder"));
        System.out.println("Position: " + searchField.getLocation());
        System.out.println("Size: " + searchField.getSize());

        searchField.sendKeys(inputValue);
        Assert.assertEquals(inputValue, searchField.getAttribute("value"));

        searchField.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        Assert.assertNotEquals(url, foxDriver.getCurrentUrl());
    }

}
