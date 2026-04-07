import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class SpotifyHomePageTest {

    WebDriver driver;

    @BeforeClass
    public void setUp() {
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
    public void testHomePageTitle() throws InterruptedException {
        driver.get("https://open.spotify.com");
        Thread.sleep(2000);
        String title = driver.getTitle();
        System.out.println("Page title: " + title);
        Assert.assertFalse(title.isEmpty(), "Page title should not be empty");
        Assert.assertTrue(title.toLowerCase().contains("spotify"),
                "Title should contain 'Spotify'");
    }

    @Test(priority = 2)
    public void testHomePageURL() throws InterruptedException {
        driver.get("https://open.spotify.com");
        Thread.sleep(2000);
        String url = driver.getCurrentUrl();
        System.out.println("Current URL: " + url);
        Assert.assertTrue(url.contains("spotify.com"),
                "URL should contain spotify.com");
    }

    @Test(priority = 3)
    public void testLoginButtonPresent() throws InterruptedException {
        driver.get("https://open.spotify.com");
        Thread.sleep(2000);
        WebElement loginBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Log in') or contains(@data-testid,'login-button')]"));
        Assert.assertTrue(loginBtn.isDisplayed(), "Login button should be visible");
        System.out.println("Login button found: " + loginBtn.getText());
    }

    @Test(priority = 4)
    public void testSignupButtonPresent() throws InterruptedException {
        driver.get("https://open.spotify.com");
        Thread.sleep(2000);
        WebElement signupBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Sign up') or contains(@data-testid,'signup-button')]"));
        Assert.assertTrue(signupBtn.isDisplayed(), "Sign up button should be visible");
        System.out.println("Signup button found: " + signupBtn.getText());
    }

    @Test(priority = 5)
    public void testPageNotEmpty() throws InterruptedException {
        driver.get("https://open.spotify.com");
        Thread.sleep(2000);
        String bodyText = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue(bodyText.length() > 0, "Page body should have content");
        System.out.println("Page loaded with content, length: " + bodyText.length());
    }

    @Test(priority = 6)
    public void testHomePageLoadsSuccessfully() throws InterruptedException {
        driver.get("https://open.spotify.com");
        Thread.sleep(2000);
        Assert.assertNotEquals(driver.getTitle(), "", "Page should have a title");
        Assert.assertTrue(driver.getCurrentUrl().startsWith("https"),
                "Page should load over HTTPS");
        System.out.println("Homepage loaded successfully over HTTPS");
    }
}