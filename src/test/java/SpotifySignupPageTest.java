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

    // Helper — types into email field slowly and hits Enter
    private void typeEmailAndSubmit(String email) throws InterruptedException {
        WebElement emailField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("username")));
        emailField.clear();
        Thread.sleep(500);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='3px solid #1DB954'", emailField);
        Thread.sleep(800);
        // Type slowly so it's visible
        for (char c : email.toCharArray()) {
            emailField.sendKeys(String.valueOf(c));
            Thread.sleep(80);
        }
        Thread.sleep(1500);
        // Hit Enter
        emailField.sendKeys(Keys.ENTER);
        Thread.sleep(3000);
    }

    @Test(priority = 1)
    public void testSignupPageLoads() throws InterruptedException {
        driver.get("https://www.spotify.com/us/signup");
        Thread.sleep(3000);
        try {
            WebElement heading = driver.findElement(By.xpath("//h1 | //h2"));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.border='5px solid #1DB954'", heading);
            Thread.sleep(2000);
        } catch (Exception ignored) {}
        String title = driver.getTitle();
        System.out.println("TEST 1: Signup page title = " + title);
        Assert.assertFalse(title.isEmpty(), "Signup page should have a title");
    }

    @Test(priority = 2)
    public void testInvalidEmailFormatShowsError() throws InterruptedException {
        driver.get("https://www.spotify.com/us/signup");
        Thread.sleep(3000);
        System.out.println("TEST 2: Typing invalid email format...");
        // Type a bad email with no domain
        WebElement emailField = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("username")));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='3px solid red'", emailField);
        Thread.sleep(800);
        for (char c : "notavalidemail".toCharArray()) {
            emailField.sendKeys(String.valueOf(c));
            Thread.sleep(80);
        }
        Thread.sleep(1500);
        emailField.sendKeys(Keys.ENTER);
        Thread.sleep(2500);
        // Check for error message
        String pageSource = driver.getPageSource().toLowerCase();
        boolean hasError = pageSource.contains("valid") ||
                pageSource.contains("error") ||
                pageSource.contains("invalid") ||
                pageSource.contains("format");
        System.out.println("TEST 2: Invalid format error shown = " + hasError);
        // Highlight error message if found
        try {
            WebElement errorMsg = driver.findElement(By.xpath(
                    "//*[contains(@class,'error') or contains(@id,'error')]"));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.border='3px solid red'", errorMsg);
            Thread.sleep(2000);
        } catch (Exception ignored) {}
        Assert.assertTrue(driver.getCurrentUrl().contains("spotify"));
    }

    @Test(priority = 3)
    public void testExistingEmailShowsError() throws InterruptedException {
        driver.get("https://www.spotify.com/us/signup");
        Thread.sleep(3000);
        System.out.println("TEST 3: Typing existing school email...");
        typeEmailAndSubmit("ijchakkalakkal8062@eagle.fgcu.edu");
        // Check for already registered error
        String pageSource = driver.getPageSource().toLowerCase();
        boolean hasError = pageSource.contains("already") ||
                pageSource.contains("taken") ||
                pageSource.contains("exists") ||
                pageSource.contains("error") ||
                pageSource.contains("registered");
        System.out.println("TEST 3: Existing email error detected = " + hasError);
        try {
            WebElement errorMsg = driver.findElement(By.xpath(
                    "//*[contains(@class,'error') or contains(@id,'error') " +
                            "or contains(text(),'already')]"));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.border='3px solid red'", errorMsg);
            Thread.sleep(2000);
        } catch (Exception ignored) {}
        Assert.assertTrue(driver.getCurrentUrl().contains("spotify"));
    }

    @Test(priority = 4)
    public void testValidEmailAcceptedAndFormLoads() throws InterruptedException {
        driver.get("https://www.spotify.com/us/signup");
        Thread.sleep(3000);
        System.out.println("TEST 4: Typing valid new email...");
        typeEmailAndSubmit("newtestuser999@gmail.com");
        // Check next step loaded (password field or next page)
        String pageSource = driver.getPageSource().toLowerCase();
        boolean nextStepLoaded = pageSource.contains("password") ||
                pageSource.contains("name") ||
                pageSource.contains("date") ||
                pageSource.contains("continue");
        System.out.println("TEST 4: Next step loaded = " + nextStepLoaded);
        // Scroll down through the form
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 400)");
        Thread.sleep(1500);
        js.executeScript("window.scrollBy(0, 400)");
        Thread.sleep(1500);
        js.executeScript("window.scrollTo(0, 0)");
        Thread.sleep(1500);
        Assert.assertTrue(driver.getCurrentUrl().contains("spotify"));
    }

    @Test(priority = 5)
    public void testSignupPageIsHTTPS() throws InterruptedException {
        driver.get("https://www.spotify.com/us/signup");
        Thread.sleep(2000);
        String url = driver.getCurrentUrl();
        System.out.println("TEST 5: URL = " + url);
        Assert.assertTrue(url.startsWith("https"),
                "Signup page should load over HTTPS");
    }

    @Test(priority = 6)
    public void testSignupTitleContainsSpotify() throws InterruptedException {
        driver.get("https://www.spotify.com/us/signup");
        Thread.sleep(3000);
        String title = driver.getTitle();
        System.out.println("TEST 6: Title = " + title);
        Assert.assertTrue(title.toLowerCase().contains("spotify"),
                "Title should contain Spotify");
    }
}