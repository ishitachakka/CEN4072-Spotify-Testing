import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class SpotifyLoginPageTest {

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
    public void testLoginPageLoads() throws InterruptedException {
        driver.get("https://accounts.spotify.com/en/login");
        Thread.sleep(3000);
        String title = driver.getTitle();
        System.out.println("Login page title: " + title);
        Assert.assertFalse(title.isEmpty(), "Login page title should not be empty");
    }

    @Test(priority = 2)
    public void testLoginPageURL() throws InterruptedException {
        driver.get("https://accounts.spotify.com/en/login");
        Thread.sleep(3000);
        String url = driver.getCurrentUrl();
        System.out.println("Login URL: " + url);
        Assert.assertTrue(url.contains("spotify"),
                "URL should contain 'spotify'");
    }

    @Test(priority = 3)
    public void testUsernameFieldPresent() throws InterruptedException {
        driver.get("https://accounts.spotify.com/en/login");
        Thread.sleep(3000);
        WebElement usernameField = driver.findElement(
                By.xpath("//input[@data-testid='login-username' or @name='username' or @id='login-username' or @autocomplete='username']"));
        Assert.assertTrue(usernameField.isDisplayed(),
                "Username field should be visible");
        System.out.println("Username field found and visible");
    }

    @Test(priority = 4)
    public void testPasswordFieldPresent() throws InterruptedException {
        driver.get("https://accounts.spotify.com/en/login");
        Thread.sleep(3000);
        // Spotify uses 2-step login - enter email first then password appears
        WebElement usernameField = driver.findElement(
                By.xpath("//input[@data-testid='login-username' or @id='login-username']"));
        usernameField.sendKeys("test@example.com");
        Thread.sleep(1000);
        WebElement continueBtn = driver.findElement(
                By.xpath("//button[@data-testid='login-button' or @id='login-button']"));
        continueBtn.click();
        Thread.sleep(3000);
        // Now password field should appear
        WebElement passwordField = driver.findElement(
                By.xpath("//input[@data-testid='login-password' or @id='login-password' or @type='password']"));
        Assert.assertTrue(passwordField.isDisplayed(),
                "Password field should be visible after entering email");
        System.out.println("TEST 4: Password field visible after email entry");
    }

    @Test(priority = 5)
    public void testLoginButtonPresent() throws InterruptedException {
        driver.get("https://accounts.spotify.com/en/login");
        Thread.sleep(3000);
        WebElement loginBtn = driver.findElement(
                By.xpath("//button[@data-testid='login-button' or @type='submit']"));
        Assert.assertTrue(loginBtn.isDisplayed(),
                "Login button should be visible");
        System.out.println("Login button found: " + loginBtn.getText());
    }

    @Test(priority = 6)
    public void testLoginPageIsHTTPS() throws InterruptedException {
        driver.get("https://accounts.spotify.com/en/login");
        Thread.sleep(3000);
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.startsWith("https"),
                "Login page should be secure (HTTPS)");
        System.out.println("Login page is secure: " + url);
    }
}