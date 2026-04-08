import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class SpotifyPremiumPageTest {

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
    public void testPremiumPageLoads() throws InterruptedException {
        driver.get("https://www.spotify.com/premium/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("premium") ||
                driver.getTitle().toLowerCase().contains("spotify"));
        System.out.println("TEST 1: Premium page title: " + driver.getTitle());
    }

    @Test(priority = 2)
    public void testPremiumPageURL() throws InterruptedException {
        driver.get("https://www.spotify.com/premium/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().contains("premium"));
        System.out.println("TEST 2: Premium URL confirmed: " + driver.getCurrentUrl());
    }

    @Test(priority = 3)
    public void testPremiumPageContainsPremiumText() throws InterruptedException {
        driver.get("https://www.spotify.com/premium/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getPageSource().contains("Premium"));
        System.out.println("TEST 3: Premium text found on page");
    }

    @Test(priority = 4)
    public void testPremiumPageContainsIndividualPlan() throws InterruptedException {
        driver.get("https://www.spotify.com/premium/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getPageSource().contains("Individual"));
        System.out.println("TEST 4: Individual plan found on premium page");
    }

    @Test(priority = 5)
    public void testPremiumPageButtonPresent() throws InterruptedException {
        driver.get("https://www.spotify.com/premium/");
        Thread.sleep(2000);
        WebElement btn = driver.findElement(By.tagName("button"));
        Assert.assertTrue(btn.isDisplayed());
        System.out.println("TEST 5: Button visible on premium page");
    }

    @Test(priority = 6)
    public void testPremiumPageIsHTTPS() throws InterruptedException {
        driver.get("https://www.spotify.com/premium/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().startsWith("https"));
        System.out.println("TEST 6: Premium page loads over HTTPS");
    }
}