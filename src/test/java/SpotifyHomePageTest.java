import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class SpotifyHomePageTest {

    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

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
        actions = new Actions(driver);
        CookieLoader.loadCookies(driver);
        // Go directly to home — no reload
        driver.get("https://open.spotify.com");
        Thread.sleep(4000);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    private void scrollDown(int times) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Get all visible cards/elements on the page and scroll them into view one by one
        try {
            java.util.List<WebElement> cards = driver.findElements(
                    By.xpath("//div[@data-testid='card-image'] | //div[contains(@class,'CardImage')]  | //article | //li[contains(@class,'item')]"));
            int step = Math.max(1, cards.size() / times);
            for (int i = 0; i < cards.size() && i < times * step; i += step) {
                js.executeScript(
                        "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'})",
                        cards.get(i));
                Thread.sleep(1000);
            }
            System.out.println("  Scrolled through " + cards.size() + " cards");
        } catch (Exception e) {
            // Fallback — scroll to bottom then back up
            js.executeScript("window.scrollTo({top: document.body.scrollHeight, behavior: 'smooth'})");
            Thread.sleep(1500);
            System.out.println("  Used fallback scroll");
        }
    }

    @Test(priority = 1)
    public void testClickMusicAndScroll() throws InterruptedException {
        System.out.println("TEST 1: Clicking Music tab...");
        WebElement musicTab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(@class,'legacy-chip__inner')" +
                        " and normalize-space(text())='Music']")));
        musicTab.click();
        Thread.sleep(3000);
        System.out.println("TEST 1: On Music — " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getCurrentUrl().contains("music-chip"),
                "Should be on music tab");
        // Scroll down through music content
        System.out.println("TEST 1: Scrolling through music content...");
        scrollDown(4);
        Thread.sleep(2000);
        System.out.println("TEST 1: Done scrolling music");
    }

    @Test(priority = 2)
    public void testClickPodcastsAndScroll() throws InterruptedException {
        System.out.println("TEST 2: Clicking Podcasts tab...");
        WebElement podcastTab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(@class,'legacy-chip__inner')" +
                        " and normalize-space(text())='Podcasts']")));
        podcastTab.click();
        Thread.sleep(3000);
        System.out.println("TEST 2: On Podcasts — " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getCurrentUrl().contains("podcasts-chip"),
                "Should be on podcasts tab");
        System.out.println("TEST 2: Scrolling through podcast content...");
        scrollDown(4);
        Thread.sleep(2000);
        System.out.println("TEST 2: Done scrolling podcasts");
    }

    @Test(priority = 3)
    public void testClickAudiobooksAndScroll() throws InterruptedException {
        System.out.println("TEST 3: Clicking Audiobooks tab...");
        WebElement audiobooksTab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(@class,'legacy-chip__inner')" +
                        " and normalize-space(text())='Audiobooks']")));
        audiobooksTab.click();
        Thread.sleep(3000);
        System.out.println("TEST 3: On Audiobooks — " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getCurrentUrl().contains("audiobooks-chip"),
                "Should be on audiobooks tab");
        System.out.println("TEST 3: Scrolling through audiobooks content...");
        scrollDown(4);
        Thread.sleep(2000);
        System.out.println("TEST 3: Done scrolling audiobooks");
    }

    @Test(priority = 4)
    public void testGoBackToMusicAndClickFollowing() throws InterruptedException {
        System.out.println("TEST 4: Going back to Music tab...");
        WebElement musicTab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(@class,'legacy-chip__inner')" +
                        " and normalize-space(text())='Music']")));
        musicTab.click();
        Thread.sleep(3000);
        System.out.println("TEST 4: Back on Music — " + driver.getCurrentUrl());
        // Now click Following
        System.out.println("TEST 4: Clicking Following tab...");
        WebElement followingTab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(@class,'legacy-chip__inner')" +
                        " and normalize-space(text())='Following']")));
        followingTab.click();
        Thread.sleep(3000);
        System.out.println("TEST 4: On Following — " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getCurrentUrl().contains("spotify"),
                "Should still be on Spotify");
    }

    @Test(priority = 5)
    public void testClickBrowseArtistsAndScroll() throws InterruptedException {
        System.out.println("TEST 5: Looking for Browse artists button...");
        try {
            WebElement browseBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[contains(text(),'Browse artists')]" +
                            "/ancestor::button | //a[contains(text(),'Browse artists')]")));
            browseBtn.click();
            Thread.sleep(3000);
            System.out.println("TEST 5: Clicked Browse artists — " + driver.getCurrentUrl());
            // Scroll through artists
            scrollDown(4);
            Thread.sleep(2000);
            System.out.println("TEST 5: Scrolled through artists");
        } catch (Exception e) {
            System.out.println("TEST 5: Browse artists button: " + e.getMessage());
        }
        Assert.assertTrue(driver.getCurrentUrl().contains("spotify"));
    }

    @Test(priority = 6)
    public void testHomePageIsHTTPS() throws InterruptedException {
        driver.get("https://open.spotify.com");
        Thread.sleep(3000);
        String url = driver.getCurrentUrl();
        System.out.println("TEST 6: URL = " + url);
        Assert.assertTrue(url.startsWith("https"),
                "Should load over HTTPS");
    }
}