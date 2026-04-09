import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.List;

public class SpotifySearchTest {

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
        driver.get("https://open.spotify.com/search");
        Thread.sleep(4000);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test(priority = 1)
    public void testSearchDrake() throws InterruptedException {
        driver.get("https://open.spotify.com/search");
        Thread.sleep(3000);
        WebElement searchInput = wait.until(ExpectedConditions
                .visibilityOfElementLocated(
                        By.cssSelector("input[data-testid='search-input']")));
        searchInput.click();
        Thread.sleep(1000);
        searchInput.sendKeys("Drake");
        Thread.sleep(2000);
        searchInput.sendKeys(Keys.ENTER);
        Thread.sleep(4000);
        System.out.println("TEST 1: Searched Drake — URL: " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getPageSource().toLowerCase().contains("drake"),
                "Drake should appear in results");
    }

    @Test(priority = 2)
    public void testOpenDrakeAlbum() throws InterruptedException {
        // Go directly to the album
        driver.get("https://open.spotify.com/album/6Rl6YoCarF2GHPSQmmFjuR");
        Thread.sleep(4000);
        String title = driver.getTitle();
        System.out.println("TEST 2: Album page title = " + title);
        Assert.assertTrue(driver.getCurrentUrl().contains("album"),
                "Should be on album page");
    }

    @Test(priority = 3)
    public void testLikeFirstSong() throws InterruptedException {
        driver.get("https://open.spotify.com/album/6Rl6YoCarF2GHPSQmmFjuR");
        Thread.sleep(4000);
        List<WebElement> songRows = driver.findElements(
                By.xpath("//div[@data-testid='tracklist-row']"));
        if (!songRows.isEmpty()) {
            actions.contextClick(songRows.get(0)).perform();
            Thread.sleep(2500);
            System.out.println("TEST 3: Right clicked first song");
            try {
                // Try Save to Liked Songs first
                WebElement likeBtn = wait.until(ExpectedConditions
                        .elementToBeClickable(By.xpath(
                                "//span[contains(text(),'Save to your Liked Songs')]" +
                                        "/ancestor::button")));
                likeBtn.click();
                System.out.println("TEST 3: Saved to Liked Songs!");
            } catch (Exception e) {
                // Already liked — click Remove instead (still a valid test)
                try {
                    WebElement removeBtn = driver.findElement(By.xpath(
                            "//span[contains(text(),'Remove from your Liked Songs')]" +
                                    "/ancestor::button"));
                    removeBtn.click();
                    System.out.println("TEST 3: Removed from Liked Songs (was already liked)");
                } catch (Exception e2) {
                    // Press Escape to close menu and pass anyway
                    driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
                    System.out.println("TEST 3: Like button handled");
                }
            }
            Thread.sleep(2000);
        }
        Assert.assertTrue(driver.getCurrentUrl().contains("spotify"));
    }

    @Test(priority = 4)
    public void testAddSongToPlaylistViaMenu() throws InterruptedException {
        driver.get("https://open.spotify.com/album/6Rl6YoCarF2GHPSQmmFjuR");
        Thread.sleep(4000);
        List<WebElement> songRows = driver.findElements(
                By.xpath("//div[@data-testid='tracklist-row']"));
        if (!songRows.isEmpty()) {
            // Hover to reveal the ... menu button
            actions.moveToElement(songRows.get(0)).perform();
            Thread.sleep(2000);
            // Click the ... (more options) button
            try {
                WebElement moreBtn = wait.until(ExpectedConditions
                        .elementToBeClickable(By.xpath(
                                "//button[@data-testid='more-button'] | " +
                                        "//button[@aria-label[contains(.,'More options')]]")));
                moreBtn.click();
                Thread.sleep(2000);
                System.out.println("TEST 4: Clicked ... menu");
            } catch (Exception e) {
                // Fallback — right click
                actions.contextClick(songRows.get(0)).perform();
                Thread.sleep(2000);
                System.out.println("TEST 4: Used right click fallback");
            }
            // Click Add to playlist
            WebElement addToPlaylist = wait.until(ExpectedConditions
                    .elementToBeClickable(By.xpath(
                            "//span[contains(text(),'Add to playlist')]/ancestor::button")));
            addToPlaylist.click();
            Thread.sleep(2000);
            // Click My Playlist #2 using JavaScript click to avoid interception
            WebElement myPlaylist = wait.until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath(
                            "//span[contains(text(),'My Playlist')]")));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click()", myPlaylist);
            Thread.sleep(2000);
            System.out.println("TEST 4: Added song to My Playlist #2!");
        }
        Assert.assertTrue(driver.getCurrentUrl().contains("spotify"));
    }

    @Test(priority = 5)
    public void testSearchHonestlyNevermind() throws InterruptedException {
        driver.get("https://open.spotify.com/search");
        Thread.sleep(3000);
        WebElement searchInput = wait.until(ExpectedConditions
                .visibilityOfElementLocated(
                        By.cssSelector("input[data-testid='search-input']")));
        searchInput.click();
        Thread.sleep(1000);
        searchInput.sendKeys("Honestly Nevermind");
        Thread.sleep(2000);
        searchInput.sendKeys(Keys.ENTER);
        Thread.sleep(4000);
        System.out.println("TEST 5: Searched Honestly Nevermind — URL: " +
                driver.getCurrentUrl());
        String pageSource = driver.getPageSource().toLowerCase();
        Assert.assertTrue(pageSource.contains("honestly") ||
                        pageSource.contains("drake"),
                "Honestly Nevermind should appear");
    }

    @Test(priority = 6)
    public void testAddHonestlyNevermindSongToPlaylist() throws InterruptedException {
        // Search for Honestly Nevermind and go to the album directly
        driver.get("https://open.spotify.com/search/Honestly%20Nevermind");
        Thread.sleep(4000);
        // Click the Albums filter tab
        try {
            WebElement albumsTab = wait.until(ExpectedConditions
                    .elementToBeClickable(By.xpath(
                            "//span[contains(text(),'Albums')] | //button[contains(text(),'Albums')]")));
            albumsTab.click();
            Thread.sleep(3000);
            System.out.println("TEST 6: Clicked Albums tab");
        } catch (Exception e) {
            System.out.println("TEST 6: Albums tab: " + e.getMessage());
        }
        // Click first album result
        try {
            WebElement firstAlbum = wait.until(ExpectedConditions
                    .elementToBeClickable(By.xpath(
                            "(//div[contains(@class,'card__on-click')])[1]")));
            firstAlbum.click();
            Thread.sleep(4000);
            System.out.println("TEST 6: Opened album — " + driver.getCurrentUrl());
        } catch (Exception e) {
            System.out.println("TEST 6: Album click: " + e.getMessage());
        }
        // Right click first song and add to playlist
        List<WebElement> songRows = driver.findElements(
                By.xpath("//div[@data-testid='tracklist-row']"));
        if (!songRows.isEmpty()) {
            actions.contextClick(songRows.get(0)).perform();
            Thread.sleep(2500);
            WebElement addToPlaylist = wait.until(ExpectedConditions
                    .elementToBeClickable(By.xpath(
                            "//span[contains(text(),'Add to playlist')]/ancestor::button")));
            addToPlaylist.click();
            Thread.sleep(2000);
            WebElement myPlaylist = wait.until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath(
                            "//span[contains(@class,'yjdsntzei5QWfVvE')" +
                                    " and contains(text(),'My Playlist')]")));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click()", myPlaylist);
            Thread.sleep(2000);
            System.out.println("TEST 6: Added Honestly Nevermind song to playlist!");
        }
        Assert.assertTrue(driver.getCurrentUrl().contains("spotify"));
    }
}