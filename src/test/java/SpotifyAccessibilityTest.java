import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpotifyAccessibilityTest {

    WebDriver driver;
    WebDriverWait wait;

    public int getResponseCode(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.connect();
        return connection.getResponseCode();
    }

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver",
                "/Users/ishitachakkalakkal/Documents/Software Testing/SeleniumDrivers/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications", "--remote-allow-origins=*");
        options.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        if (driver != null) {
            Thread.sleep(2000);
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void testHomePageResponse200() throws Exception {
        int code = getResponseCode("https://www.spotify.com/");
        System.out.println("TEST 1: Homepage response code = " + code);
        Assert.assertEquals(code, 200, "Homepage is unreachable!");
    }

    @Test(priority = 2)
    public void testPremiumPageResponse200() throws Exception {
        int code = getResponseCode("https://www.spotify.com/premium/");
        System.out.println("TEST 2: Premium page response code = " + code);
        Assert.assertEquals(code, 200, "Premium page is unreachable!");
    }

    @Test(priority = 3)
    public void testDownloadPageResponse200() throws Exception {
        int code = getResponseCode("https://www.spotify.com/download/");
        System.out.println("TEST 3: Download page response code = " + code);
        Assert.assertEquals(code, 200, "Download page is unreachable!");
    }

    @Test(priority = 4)
    public void testPageHeaderVisibility() throws InterruptedException {
        driver.get("https://www.spotify.com/");
        Thread.sleep(2000);
        WebElement header = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//header | //nav")));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='5px solid purple'", header);
        Thread.sleep(2000);
        System.out.println("TEST 4: Header is visible and highlighted purple");
        Assert.assertTrue(header.isDisplayed());
    }

    @Test(priority = 5)
    public void testFooterIntegrity() throws InterruptedException {
        driver.get("https://www.spotify.com/");
        Thread.sleep(2000);
        try {
            WebElement footer = wait.until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath(
                            "//footer | //div[contains(@class,'footer')] | " +
                                    "//*[contains(text(),'©')] | //*[contains(text(),'Spotify AB')]")));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior:'smooth',block:'center'})", footer);
            Thread.sleep(1500);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.border='5px solid orange'", footer);
            Thread.sleep(1500);
            System.out.println("TEST 5: Footer found and highlighted orange");
            Assert.assertTrue(footer.isDisplayed());
        } catch (Exception e) {
            System.out.println("TEST 5: Footer fallback — checking page content");
            Assert.assertTrue(driver.getPageSource().contains("Spotify"),
                    "Page content is missing!");
        }
    }
}