package ntudp.atvd.lab2;

import org.junit.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class uniSiteTest
{
    private WebDriver foxDriver;
    private final String url = "https://www.nmu.org.ua/ua/";

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-fullscreen");
        options.setImplicitWaitTimeout(Duration.ofSeconds(15));
        foxDriver = new FirefoxDriver();
    }

    @BeforeMethod
    public void precondition() { foxDriver.get(url); }

    @AfterClass(alwaysRun = true)
    public void tearDown() { foxDriver.quit(); }

    @Test
    public void testHeaderExists() {
        WebElement header = foxDriver.findElement(By.id("header"));
        Assert.assertNotNull(header);
    }
}
