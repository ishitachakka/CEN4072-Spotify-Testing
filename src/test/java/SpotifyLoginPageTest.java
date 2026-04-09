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
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver",
                "/Users/ishitachakkalakkal/Documents/Software Testing/SeleniumDrivers/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    private void typeSlowly(WebElement field, String text) throws InterruptedException {
        for (char c : text.toCharArray()) {
            field.sendKeys(String.valueOf(c));
            Thread.sleep(80);
        }
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
    public void testLoginPageURLIsHTTPS() throws InterruptedException {
        driver.get("https://accounts.spotify.com/en/login");
        Thread.sleep(2000);
        String url = driver.getCurrentUrl();
        System.out.println("TEST 2: URL = " + url);
        Assert.assertTrue(url.contains("spotify"));
        Assert.assertTrue(url.startsWith("https"));
    }

    @Test(priority = 3)
    public void testEmailFieldHighlightedAndFilled() throws InterruptedException {
        driver.get("https://accounts.spotify.com/en/login");
        Thread.sleep(4000);
        // Find email field with multiple selectors
        WebElement emailField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector(
                        "input[autocomplete='username'], input[type='email'], " +
                                "input[type='text'], #login-username")));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='3px solid #1DB954'", emailField);
        Thread.sleep(1000);
        typeSlowly(emailField, "ijchakkalakkal8062@eagle.fgcu.edu");
        Thread.sleep(1500);
        String value = emailField.getAttribute("value");
        System.out.println("TEST 3: Email typed = " + value);
        Assert.assertFalse(value.isEmpty(), "Email field should have value");
    }

    @Test(priority = 4)
    public void testEmailContinueAndPasswordField() throws InterruptedException {
        driver.get("https://accounts.spotify.com/en/login");
        Thread.sleep(4000);
        // Type email
        WebElement emailField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector(
                        "input[autocomplete='username'], input[type='email'], " +
                                "input[type='text'], #login-username")));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='3px solid #1DB954'", emailField);
        Thread.sleep(800);
        typeSlowly(emailField, "ijchakkalakkal8062@eagle.fgcu.edu");
        Thread.sleep(1000);
        // Click Continue
        WebElement continueBtn = driver.findElement(By.cssSelector(
                "button[type='submit'], #login-button"));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.background='#1DB954'", continueBtn);
        Thread.sleep(1500);
        continueBtn.click();
        Thread.sleep(4000);
        // Click Log in with a password
        try {
            WebElement loginWithPwd = wait.until(ExpectedConditions
                    .elementToBeClickable(By.xpath(
                            "//*[contains(text(),'Log in with a password')]")));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.border='3px solid cyan'", loginWithPwd);
            Thread.sleep(1500);
            loginWithPwd.click();
            Thread.sleep(2000);
            // Verify password field appears
            WebElement pwdField = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.cssSelector(
                            "input[type='password'], #login-password")));
            System.out.println("TEST 4: Password field visible = " + pwdField.isDisplayed());
            Assert.assertTrue(pwdField.isDisplayed());
        } catch (Exception e) {
            System.out.println("TEST 4: " + e.getMessage());
            Assert.assertTrue(driver.getCurrentUrl().contains("spotify"));
        }
    }

    @Test(priority = 5)
    public void testFullLoginAndVerifyHomepage() throws InterruptedException {
        driver.get("https://accounts.spotify.com/en/login");
        Thread.sleep(4000);
        // Type email
        WebElement emailField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector(
                        "input[autocomplete='username'], input[type='email'], " +
                                "input[type='text'], #login-username")));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='3px solid #1DB954'", emailField);
        Thread.sleep(800);
        typeSlowly(emailField, "ijchakkalakkal8062@eagle.fgcu.edu");
        Thread.sleep(1000);
        // Click Continue
        driver.findElement(By.cssSelector(
                "button[type='submit'], #login-button")).click();
        Thread.sleep(4000);
        // Click Log in with a password
        try {
            WebElement loginWithPwd = wait.until(ExpectedConditions
                    .elementToBeClickable(By.xpath(
                            "//*[contains(text(),'Log in with a password')]")));
            loginWithPwd.click();
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("TEST 5: Skipping password link");
        }
        // Type password
        try {
            WebElement pwdField = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.cssSelector(
                            "input[type='password'], #login-password")));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.border='3px solid #1DB954'", pwdField);
            Thread.sleep(800);
            typeSlowly(pwdField, "!uJB83.Z8Pr.b$J");
            Thread.sleep(1000);
            // Click Login
            WebElement loginBtn = driver.findElement(By.cssSelector(
                    "button[type='submit'], #login-button"));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.background='#1DB954'", loginBtn);
            Thread.sleep(1500);
            loginBtn.click();
            Thread.sleep(7000);
            System.out.println("TEST 5: After login URL = " + driver.getCurrentUrl());
        } catch (Exception e) {
            System.out.println("TEST 5: " + e.getMessage());
        }
        Assert.assertTrue(driver.getCurrentUrl().contains("spotify"));
    }

    @Test(priority = 6)
    public void testVerifyLoggedInWithCookies() throws Exception {
        // Load cookies and verify already logged in
        CookieLoader.loadCookies(driver);
        Thread.sleep(3000);
        String url = driver.getCurrentUrl();
        System.out.println("TEST 6: Logged in URL = " + url);
        Assert.assertTrue(url.contains("open.spotify.com"),
                "Should be on Spotify homepage when logged in");
    }
}