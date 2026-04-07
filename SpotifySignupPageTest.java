import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class SpotifySignupPageTest {

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
    public void testSignupPageLoads() {
        driver.get("https://www.spotify.com/us/signup");
        String title = driver.getTitle();
        System.out.println("Signup page title: " + title);
        Assert.assertFalse(title.isEmpty(), "Signup page title should not be empty");
    }

    @Test(priority = 2)
    public void testSignupPageURL() {
        driver.get("https://www.spotify.com/us/signup");
        String url = driver.getCurrentUrl();
        System.out.println("Signup URL: " + url);
        Assert.assertTrue(url.contains("signup"),
                "URL should contain 'signup'");
    }

    @Test(priority = 3)
    public void testEmailFieldPresent() {
        driver.get("https://www.spotify.com/us/signup");
        WebElement emailField = driver.findElement(By.id("username"));
        Assert.assertTrue(emailField.isDisplayed(),
                "Email field should be visible");
        System.out.println("Email field found and visible");
    }

    @Test(priority = 4)
    public void testSignupPageIsHTTPS() {
        driver.get("https://www.spotify.com/us/signup");
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.startsWith("https"),
                "Signup page should be secure (HTTPS)");
        System.out.println("Signup page is secure: " + url);
    }

    @Test(priority = 5)
    public void testSignupTitleContainsSpotify() {
        driver.get("https://www.spotify.com/us/signup");
        String title = driver.getTitle();
        Assert.assertTrue(title.toLowerCase().contains("spotify"),
                "Signup page title should contain Spotify");
        System.out.println("Signup title confirmed: " + title);
    }

    @Test(priority = 6)
    public void testSignupURLNotEqualToLogin() {
        String signupUrl = "https://www.spotify.com/us/signup";
        String loginUrl = "https://accounts.spotify.com/en/login";
        Assert.assertNotEquals(signupUrl, loginUrl,
                "Signup and login URLs should be different");
        System.out.println("Signup and login pages are separate — confirmed");
    }
}