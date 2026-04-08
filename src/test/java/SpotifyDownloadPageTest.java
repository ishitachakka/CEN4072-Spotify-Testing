import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class SpotifyDownloadPageTest {

    WebDriver driver;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver",
                "/Users/ishitachakkalakkal/Documents/Software Testing/SeleniumDrivers/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test(priority = 1)
    public void testDownloadPageLoads() throws InterruptedException {
        driver.get("https://www.spotify.com/download/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getTitle().length() > 0);
        System.out.println("TEST 1: Download page title: " + driver.getTitle());
    }

    @Test(priority = 2)
    public void testDownloadPageURL() throws InterruptedException {
        driver.get("https://www.spotify.com/download/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().contains("download"));
        System.out.println("TEST 2: Download URL confirmed: " + driver.getCurrentUrl());
    }

    @Test(priority = 3)
    public void testDownloadPageContainsMac() throws InterruptedException {
        driver.get("https://www.spotify.com/download/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getPageSource().contains("Mac"));
        System.out.println("TEST 3: Mac download option found");
    }

    @Test(priority = 4)
    public void testDownloadPageContainsMobile() throws InterruptedException {
        driver.get("https://www.spotify.com/download/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getPageSource().toLowerCase().contains("mobile") ||
                driver.getPageSource().contains("Android") ||
                driver.getPageSource().contains("iOS"));
        System.out.println("TEST 4: Mobile download option found");
    }

    @Test(priority = 5)
    public void testDownloadPageHasLinks() throws InterruptedException {
        driver.get("https://www.spotify.com/download/");
        Thread.sleep(2000);
        WebElement link = driver.findElement(By.tagName("a"));
        Assert.assertTrue(link.isDisplayed());
        System.out.println("TEST 5: Links found on download page");
    }

    @Test(priority = 6)
    public void testDownloadPageIsHTTPS() throws InterruptedException {
        driver.get("https://www.spotify.com/download/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().startsWith("https"));
        System.out.println("TEST 6: Download page loads over HTTPS");
    }
}