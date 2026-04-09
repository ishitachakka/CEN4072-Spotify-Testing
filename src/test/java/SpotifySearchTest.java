import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class SpotifySearchTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "/Users/ishitachakkalakkal/Documents/Software Testing/SeleniumDrivers/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        loginToSpotify();
    }

    private void loginToSpotify() throws InterruptedException {
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
        System.out.println("Logged in — URL: " + driver.getCurrentUrl());
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test(priority = 1)
    public void testSearchPageLoads() throws InterruptedException {
        driver.get("https://open.spotify.com/search");
        Thread.sleep(3000);
        Assert.assertFalse(driver.getTitle().isEmpty(),
                "Search page should have a title");
        System.out.println("TEST 1: Search page loaded — " + driver.getTitle());
    }

    @Test(priority = 2)
    public void testSearchForDrake() throws InterruptedException {
        driver.get("https://open.spotify.com/search");
        Thread.sleep(3000);
        WebElement searchInput = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(
                        "//input[@placeholder='What do you want to play?' or @data-testid='search-input']")));
        searchInput.click();
        Thread.sleep(1000);
        searchInput.sendKeys("Drake");
        Thread.sleep(3000);
        System.out.println("TEST 2: Searched Drake — URL: " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getPageSource().toLowerCase().contains("drake"),
                "Drake should appear in results");
    }

    @Test(priority = 3)
    public void testSearchForHonestlyNevermind() throws InterruptedException {
        driver.get("https://open.spotify.com/search");
        Thread.sleep(3000);
        WebElement searchInput = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(
                        "//input[@placeholder='What do you want to play?' or @data-testid='search-input']")));
        searchInput.click();
        Thread.sleep(1000);
        searchInput.sendKeys("Honestly Nevermind");
        Thread.sleep(3000);
        System.out.println("TEST 3: Searched Honestly Nevermind");
        String pageSource = driver.getPageSource().toLowerCase();
        Assert.assertTrue(pageSource.contains("honestly") || pageSource.contains("drake"),
                "Album should appear in results");
    }

    @Test(priority = 4)
    public void testSearchResultsHaveContent() throws InterruptedException {
        driver.get("https://open.spotify.com/search");
        Thread.sleep(3000);
        WebElement searchInput = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(
                        "//input[@placeholder='What do you want to play?' or @data-testid='search-input']")));
        searchInput.sendKeys("Drake");
        Thread.sleep(3000);
        String body = driver.findElement(By.tagName("body")).getText();
        System.out.println("TEST 4: Body length after search = " + body.length());
        Assert.assertTrue(body.length() > 100, "Results should not be empty");
    }

    @Test(priority = 5)
    public void testClearSearchAndSearchAgain() throws InterruptedException {
        driver.get("https://open.spotify.com/search");
        Thread.sleep(3000);
        WebElement searchInput = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(
                        "//input[@placeholder='What do you want to play?' or @data-testid='search-input']")));
        searchInput.sendKeys("Drake");
        Thread.sleep(2000);
        searchInput.clear();
        Thread.sleep(1000);
        searchInput.sendKeys("Honestly Nevermind Drake");
        Thread.sleep(3000);
        System.out.println("TEST 5: Cleared and re-searched");
        Assert.assertTrue(driver.getCurrentUrl().contains("spotify"),
                "Should still be on Spotify");
    }

    @Test(priority = 6)
    public void testSearchPageIsHTTPS() throws InterruptedException {
        driver.get("https://open.spotify.com/search");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().startsWith("https"),
                "Should be HTTPS");
        System.out.println("TEST 6: Search page is HTTPS — " + driver.getCurrentUrl());
    }
}