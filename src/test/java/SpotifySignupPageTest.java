import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class SpotifySignupPageTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",
                "/Users/ishitachakkalakkal/Documents/Software Testing/SeleniumDrivers/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test(priority = 1)
    public void testSignupPageLoads() throws InterruptedException {
        driver.get("https://www.spotify.com/us/signup");
        Thread.sleep(3000);
        String title = driver.getTitle();
        System.out.println("TEST 1: Signup title = " + title);
        Assert.assertFalse(title.isEmpty(), "Signup page should have a title");
    }

    @Test(priority = 2)
    public void testSignupPageURL() throws InterruptedException {
        driver.get("https://www.spotify.com/us/signup");
        Thread.sleep(3000);
        Assert.assertTrue(driver.getCurrentUrl().contains("signup"),
                "URL should contain signup");
        System.out.println("TEST 2: Signup URL = " + driver.getCurrentUrl());
    }

    @Test(priority = 3)
    public void testEmailFieldAcceptsInput() throws InterruptedException {
        driver.get("https://www.spotify.com/us/signup");
        Thread.sleep(3000);
        WebElement emailField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("username")));
        emailField.sendKeys("testuser@example.com");
        Thread.sleep(1500);
        Assert.assertEquals(emailField.getAttribute("value"),
                "testuser@example.com", "Email field should accept input");
        System.out.println("TEST 3: Email field accepted input");
    }

    @Test(priority = 4)
    public void testScrollDownSignupForm() throws InterruptedException {
        driver.get("https://www.spotify.com/us/signup");
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 400)");
        Thread.sleep(1500);
        js.executeScript("window.scrollBy(0, 400)");
        Thread.sleep(1500);
        Long scrollY = (Long) js.executeScript("return window.scrollY");
        System.out.println("TEST 4: Scrolled signup form to Y = " + scrollY);
        Assert.assertTrue(scrollY > 0, "Page should have scrolled");
    }

    @Test(priority = 5)
    public void testSignupPageIsHTTPS() throws InterruptedException {
        driver.get("https://www.spotify.com/us/signup");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().startsWith("https"),
                "Signup page should be HTTPS");
        System.out.println("TEST 5: Signup page is HTTPS");
    }

    @Test(priority = 6)
    public void testSignupTitleContainsSpotify() throws InterruptedException {
        driver.get("https://www.spotify.com/us/signup");
        Thread.sleep(3000);
        String title = driver.getTitle();
        Assert.assertTrue(title.toLowerCase().contains("spotify"),
                "Title should contain Spotify");
        System.out.println("TEST 6: Signup title contains Spotify — " + title);
    }
}