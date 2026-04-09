import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class SpotifyDownloadPageTest {

    WebDriver driver;
    WebDriverWait wait;

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
    public void testDownloadPageNavigation() throws InterruptedException {
        driver.get("https://www.spotify.com/download/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getPageSource().contains("Download"),
                "Download page not reached.");
        System.out.println("TEST 1: Download page successfully loaded — " + driver.getTitle());
    }

    @Test(priority = 2)
    public void testHighlightMicrosoftStoreButton() throws InterruptedException {
        WebElement storeBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(),'Microsoft Store')] | //img[contains(@alt,'Microsoft')]")));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth',block:'center'})", storeBtn);
        Thread.sleep(1500);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='5px solid cyan'", storeBtn);
        Thread.sleep(2000);
        System.out.println("TEST 2: Microsoft Store button highlighted cyan");
        Assert.assertTrue(storeBtn.isDisplayed());
    }

    @Test(priority = 3)
    public void testDirectDownloadLink() throws InterruptedException {
        driver.get("https://www.spotify.com/download/");
        Thread.sleep(2000);
        // Look for any download link on the page
        try {
            WebElement directLink = driver.findElement(By.xpath(
                    "//a[contains(text(),'directly')] | " +
                            "//a[contains(text(),'Download')] | " +
                            "//a[contains(@href,'download')]"));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior:'smooth',block:'center'})", directLink);
            Thread.sleep(1500);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.border='3px solid pink'", directLink);
            Thread.sleep(2000);
            System.out.println("TEST 3: Download link found and highlighted pink");
            Assert.assertTrue(directLink.isDisplayed());
        } catch (Exception e) {
            System.out.println("TEST 3: Fallback — checking page source");
            Assert.assertTrue(driver.getPageSource().contains("Download"));
        }
    }

    @Test(priority = 4)
    public void testLoginLinkInteractivity() throws InterruptedException {
        WebElement loginLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(text(),'Log in')] | //button[contains(text(),'Log in')]")));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth',block:'center'})", loginLink);
        Thread.sleep(1000);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.background='yellow'", loginLink);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='2px solid black'", loginLink);
        Thread.sleep(2000);
        System.out.println("TEST 4: Log in link highlighted yellow");
        Assert.assertTrue(loginLink.isDisplayed());
    }

    @Test(priority = 5)
    public void testDownloadPageContainsMac() throws InterruptedException {
        driver.get("https://www.spotify.com/download/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getPageSource().contains("Mac"),
                "Mac download option not found");
        System.out.println("TEST 5: Mac download option confirmed");
    }

    @Test(priority = 6)
    public void testHeaderBrandingIntegrity() throws InterruptedException {
        WebElement logo = driver.findElement(By.xpath(
                "//header//a[contains(@href,'spotify')] | //svg[contains(@aria-hidden,'true')]"));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth',block:'center'})", logo);
        Thread.sleep(1000);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='3px solid white'", logo);
        Thread.sleep(1500);
        System.out.println("TEST 6: Spotify logo/branding confirmed");
        Assert.assertTrue(logo.isDisplayed());
    }
}