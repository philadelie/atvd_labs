package ntudp.atvd.lab2;

import org.junit.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class UniSiteTesting {
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
    public void precondition() {
        foxDriver.get(url);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        foxDriver.quit();
    }

    @Test
    public void testHeaderExists() {
        WebElement header = foxDriver.findElement(By.id("header"));
        Assert.assertNotNull(header);
    }

    @Test
    public void testClickOnForStudent() {
        WebElement forStudentButton = foxDriver.findElement(By.xpath("/html/body/center/div[4]/div/div[1]/ul/li[4]/a"));
        Assert.assertNotNull(forStudentButton);
        forStudentButton.click();
        Assert.assertEquals(foxDriver.getCurrentUrl(), url);
    }

    @Test
    public void testSearchField() {
        String studentPage = url + "content/students/";
        foxDriver.get(studentPage);

        WebElement searchField = foxDriver.findElement(By.tagName("input"));
        Assert.assertNotNull(searchField);

        System.out.printf("\nAttribute name: %s", searchField.getAttribute("name"));
        System.out.printf("\nAttribute id: %s", searchField.getAttribute("id"));
        System.out.printf("\nAttribute type: %s", searchField.getAttribute("type"));
        System.out.printf("\nAttribute value: %s", searchField.getAttribute("value"));
        System.out.printf("\nPosition: %s", searchField.getLocation());
        System.out.printf("\nSize: %s", searchField.getSize());

        String inputValue = "I need info";
        searchField.sendKeys(inputValue);
        Assert.assertEquals(inputValue, searchField.getText());

        searchField.sendKeys(Keys.ENTER);
        Assert.assertNotEquals(studentPage, foxDriver.getCurrentUrl());
    }
}
