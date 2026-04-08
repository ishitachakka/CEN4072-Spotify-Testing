import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpotifyAccessibilityTest {

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
    public void testHomePageResponse200() throws Exception {
        URL url = new URL("https://www.spotify.com/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int code = connection.getResponseCode();
        System.out.println("TEST 1: Homepage response code: " + code);
        Assert.assertEquals(code, 200);
    }

    @Test(priority = 2)
    public void testPremiumPageResponse200() throws Exception {
        URL url = new URL("https://www.spotify.com/premium/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        int code = connection.getResponseCode();
        System.out.println("TEST 2: Premium page response code: " + code);
        Assert.assertEquals(code, 200);
    }

    @Test(priority = 3)
    public void testDownloadPageResponse200() throws Exception {
        URL url = new URL("https://www.spotify.com/download/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        int code = connection.getResponseCode();
        System.out.println("TEST 3: Download page response code: " + code);
        Assert.assertEquals(code, 200);
    }

    @Test(priority = 4)
    public void testPageTitleExists() throws InterruptedException {
        driver.get("https://www.spotify.com/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getTitle().length() > 0);
        System.out.println("TEST 4: Page title exists: " + driver.getTitle());
    }

    @Test(priority = 5)
    public void testURLisHTTPS() throws InterruptedException {
        driver.get("https://www.spotify.com/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().startsWith("https"));
        System.out.println("TEST 5: URL is HTTPS: " + driver.getCurrentUrl());
    }

    @Test(priority = 6)
    public void testBodyContentExists() throws InterruptedException {
        driver.get("https://www.spotify.com/");
        Thread.sleep(2000);
        String body = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue(body.length() > 0);
        System.out.println("TEST 6: Page body has content, length: " + body.length());
    }
}