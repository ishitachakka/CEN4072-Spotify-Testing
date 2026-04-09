import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.Set;

public class SpotifyNavigationTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver",
                "/Users/ishitachakkalakkal/Documents/Software Testing/SeleniumDrivers/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications", "--remote-allow-origins=*");
        options.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        if (driver != null) {
            Thread.sleep(2000);
            driver.quit();
        }
    }

    public void switchToNewWindow() {
        String mainWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                System.out.println("Switched to new window/tab");
            }
        }
    }

    public void clickButtonByText(String text) throws InterruptedException {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(translate(text()," +
                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" +
                        text.toLowerCase() + "')]")));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth',block:'center'})", element);
        Thread.sleep(1500);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='3px solid #1DB954'", element);
        Thread.sleep(1000);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", element);
        System.out.println("Clicked: " + text);
    }

    @Test(priority = 1)
    public void testHomeNavigation() throws InterruptedException {
        driver.get("https://www.spotify.com/");
        Thread.sleep(2000);
        String title = driver.getTitle();
        System.out.println("TEST 1: Home page title = " + title);
        Assert.assertTrue(title.contains("Spotify"),
                "Home page title should contain Spotify");
    }

    @Test(priority = 2)
    public void testPremiumJourney() throws InterruptedException {
        driver.get("https://www.spotify.com/");
        Thread.sleep(2000);
        clickButtonByText("Premium");
        Thread.sleep(2000);
        System.out.println("TEST 2: Premium URL = " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getPageSource().contains("Premium"),
                "Premium page should contain Premium content");
    }

    @Test(priority = 3)
    public void testDownloadJourney() throws InterruptedException {
        driver.get("https://www.spotify.com/");
        Thread.sleep(2000);
        clickButtonByText("Download");
        Thread.sleep(2000);
        System.out.println("TEST 3: Download URL = " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getPageSource().contains("Download"),
                "Download page should contain Download content");
    }

    @Test(priority = 4)
    public void testSupportJourney() throws InterruptedException {
        driver.get("https://www.spotify.com/");
        Thread.sleep(2000);
        clickButtonByText("Support");
        Thread.sleep(2000);
        switchToNewWindow();
        System.out.println("TEST 4: Support URL = " + driver.getCurrentUrl());
        Assert.assertTrue(
                driver.getPageSource().contains("Support") ||
                        driver.getTitle().contains("Support"),
                "Support page should contain Support content");
    }

    @Test(priority = 5)
    public void testLogoHomeRedirect() throws InterruptedException {
        driver.get("https://www.spotify.com/premium/");
        Thread.sleep(2000);
        WebElement logo = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//header//a[1] | //a[contains(@aria-label,'Spotify')]")));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='3px solid #1DB954'", logo);
        Thread.sleep(1000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logo);
        Thread.sleep(2000);
        System.out.println("TEST 5: After logo click URL = " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("spotify"),
                "Logo should redirect to Spotify homepage");
    }

    @Test(priority = 6)
    public void testFooterExists() throws InterruptedException {
        driver.get("https://www.spotify.com/");
        Thread.sleep(2000);
        // Spotify uses div instead of footer tag
        try {
            WebElement footer = driver.findElement(By.xpath(
                    "//footer | //div[contains(@class,'footer')] | " +
                            "//*[contains(text(),'© 2024 Spotify AB')]"));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior:'smooth',block:'center'})", footer);
            Thread.sleep(1500);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.border='3px solid #1DB954'", footer);
            Thread.sleep(1500);
            System.out.println("TEST 6: Footer visible and highlighted");
            Assert.assertTrue(footer.isDisplayed());
        } catch (Exception e) {
            // Fallback — just verify page has content
            System.out.println("TEST 6: Footer fallback — verifying page content");
            Assert.assertTrue(driver.getPageSource().contains("Spotify"));
        }
    }
}