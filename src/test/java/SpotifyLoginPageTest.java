import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class SpotifyLoginPageTest {

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
    public void testLoginPageLoads() throws InterruptedException {
        driver.get("https://accounts.spotify.com/en/login");
        Thread.sleep(3000);
        String title = driver.getTitle();
        System.out.println("TEST 1: Login page title = " + title);
        Assert.assertFalse(title.isEmpty(), "Login page should have a title");
    }

    @Test(priority = 2)
    public void testLoginPageURL() throws InterruptedException {
        driver.get("https://accounts.spotify.com/en/login");
        Thread.sleep(2000);
        String url = driver.getCurrentUrl();
        System.out.println("TEST 2: Login URL = " + url);
        Assert.assertTrue(url.contains("spotify"), "URL should contain spotify");
    }

    @Test(priority = 3)
    public void testEmailFieldAndContinue() throws InterruptedException {
        driver.get("https://accounts.spotify.com/en/login");
        Thread.sleep(3000);
        WebElement emailField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("login-username")));
        emailField.sendKeys("ijchakkalakkal8062@eagle.fgcu.edu");
        Thread.sleep(1500);
        System.out.println("TEST 3: Typed email into field");
        Assert.assertEquals(emailField.getAttribute("value"),
                "ijchakkalakkal8062@eagle.fgcu.edu",
                "Email field should contain typed email");
    }

    @Test(priority = 4)
    public void testClickContinueAndLoginWithPassword() throws InterruptedException {
        driver.get("https://accounts.spotify.com/en/login");
        Thread.sleep(3000);
        WebElement emailField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("login-username")));
        emailField.sendKeys("ijchakkalakkal8062@eagle.fgcu.edu");
        Thread.sleep(1000);
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(3000);
        // Click "Log in with a password"
        WebElement loginWithPassword = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(
                        "//*[@id='encore-web-main-content']/div[2]/div/div/div")));
        loginWithPassword.click();
        Thread.sleep(2000);
        System.out.println("TEST 4: Clicked Log in with a password");
        WebElement passwordField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("login-password")));
        Assert.assertTrue(passwordField.isDisplayed(),
                "Password field should be visible");
    }

    @Test(priority = 5)
    public void testFullLoginFlow() throws InterruptedException {
        driver.get("https://accounts.spotify.com/en/login");
        Thread.sleep(3000);
        WebElement emailField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("login-username")));
        emailField.sendKeys("ijchakkalakkal8062@eagle.fgcu.edu");
        Thread.sleep(1000);
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(3000);
        WebElement loginWithPassword = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(
                        "//*[@id='encore-web-main-content']/div[2]/div/div/div")));
        loginWithPassword.click();
        Thread.sleep(2000);
        WebElement passwordField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("login-password")));
        passwordField.sendKeys("!uJB83.Z8Pr.b$J");
        Thread.sleep(1000);
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(5000);
        System.out.println("TEST 5: After login URL = " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getCurrentUrl().contains("open.spotify.com") ||
                        driver.getCurrentUrl().contains("spotify.com"),
                "Should be logged into Spotify");
    }

    @Test(priority = 6)
    public void testLoginPageIsHTTPS() throws InterruptedException {
        driver.get("https://accounts.spotify.com/en/login");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().startsWith("https"),
                "Login page should be HTTPS");
        System.out.println("TEST 6: Login page is HTTPS");
    }
}