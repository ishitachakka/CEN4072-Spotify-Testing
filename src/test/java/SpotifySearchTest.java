import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class SpotifySearchTest {

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
    public void testSearchPageURL() throws InterruptedException {
        driver.get("https://open.spotify.com/search");
        Thread.sleep(2000);
        String url = driver.getCurrentUrl();
        System.out.println("Search URL: " + url);
        Assert.assertTrue(url.contains("spotify.com"),
                "Search page URL should contain spotify.com");
    }

    @Test(priority = 2)
    public void testSearchPageTitle() throws InterruptedException {
        driver.get("https://open.spotify.com/search");
        Thread.sleep(2000);
        String title = driver.getTitle();
        System.out.println("Search page title: " + title);
        Assert.assertFalse(title.isEmpty(), "Search page title should not be empty");
    }

    @Test(priority = 3)
    public void testSearchPageLoads() throws InterruptedException {
        driver.get("https://open.spotify.com/search");
        Thread.sleep(2000);
        String bodyText = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue(bodyText.length() > 0, "Search page body should have content");
        System.out.println("Search page loaded with content, length: " + bodyText.length());
    }

    @Test(priority = 4)
    public void testSearchURLContainsSearch() throws InterruptedException {
        driver.get("https://open.spotify.com/search");
        Thread.sleep(2000);
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("search"),
                "URL should contain 'search'");
        System.out.println("Search URL confirmed: " + url);
    }

    @Test(priority = 5)
    public void testSearchPageHTTPS() throws InterruptedException {
        driver.get("https://open.spotify.com/search");
        Thread.sleep(2000);
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.startsWith("https"),
                "Search page should load over HTTPS");
        System.out.println("Search page loads securely: " + url);
    }

    @Test(priority = 6)
    public void testSearchPageNotEqualToHome() throws InterruptedException {
        driver.get("https://open.spotify.com/search");
        Thread.sleep(2000);
        String searchUrl = driver.getCurrentUrl();
        driver.get("https://open.spotify.com");
        Thread.sleep(2000);
        String homeUrl = driver.getCurrentUrl();
        Assert.assertNotEquals(searchUrl, homeUrl,
                "Search URL should be different from home URL");
        System.out.println("Search and home URLs are different — confirmed");
    }
}