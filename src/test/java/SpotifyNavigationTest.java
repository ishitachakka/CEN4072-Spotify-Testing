import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class SpotifyNavigationTest {

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
    public void testHomeNavigation() throws InterruptedException {
        driver.get("https://www.spotify.com/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getTitle().contains("Spotify"));
        System.out.println("TEST 1: Home page title contains Spotify");
    }

    @Test(priority = 2)
    public void testPremiumNavigation() throws InterruptedException {
        driver.get("https://www.spotify.com/");
        Thread.sleep(2000);
        driver.get("https://www.spotify.com/premium/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().contains("premium"));
        System.out.println("TEST 2: Premium page URL confirmed: " + driver.getCurrentUrl());
    }

    @Test(priority = 3)
    public void testSupportNavigation() throws InterruptedException {
        driver.get("https://support.spotify.com/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().contains("support"));
        System.out.println("TEST 3: Support page URL confirmed: " + driver.getCurrentUrl());
    }

    @Test(priority = 4)
    public void testDownloadNavigation() throws InterruptedException {
        driver.get("https://www.spotify.com/download/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().contains("download"));
        System.out.println("TEST 4: Download page URL confirmed: " + driver.getCurrentUrl());
    }

    @Test(priority = 5)
    public void testFooterExists() throws InterruptedException {
        driver.get("https://www.spotify.com/");
        Thread.sleep(2000);
        WebElement footer = driver.findElement(By.tagName("footer"));
        Assert.assertTrue(footer.isDisplayed());
        System.out.println("TEST 5: Footer is visible on homepage");
    }

    @Test(priority = 6)
    public void testAllPagesLoadOverHTTPS() throws InterruptedException {
        driver.get("https://www.spotify.com/premium/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().startsWith("https"));
        System.out.println("TEST 6: Premium page loads over HTTPS");
    }
}