import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class SpotifyPremiumPageTest {

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

    @Test(priority = 1)
    public void testPageLoadAndHeading() throws InterruptedException {
        driver.get("https://www.spotify.com/premium/");
        Thread.sleep(2000);
        WebElement mainHeading = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h1 | //h2[contains(@class,'heading')]")));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='5px solid yellow'", mainHeading);
        Thread.sleep(2000);
        System.out.println("TEST 1: Heading found and highlighted yellow");
        Assert.assertTrue(mainHeading.isDisplayed());
    }

    @Test(priority = 2)
    public void testPremiumContentPresence() throws InterruptedException {
        driver.get("https://www.spotify.com/premium/");
        Thread.sleep(2000);
        String bodyText = driver.findElement(By.tagName("body")).getText();
        boolean hasContent = bodyText.contains("Premium") || bodyText.contains("Music");
        System.out.println("TEST 2: Premium content verified on page");
        Thread.sleep(2000);
        Assert.assertTrue(hasContent, "Premium page should contain Premium or Music content");
    }

    @Test(priority = 3)
    public void testPremiumPageContainsIndividualPlan() throws InterruptedException {
        driver.get("https://www.spotify.com/premium/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getPageSource().contains("Individual"),
                "Individual plan should be visible");
        System.out.println("TEST 3: Individual plan found on premium page");
    }

    @Test(priority = 4)
    public void testScrollToFooter() throws InterruptedException {
        driver.get("https://www.spotify.com/premium/");
        Thread.sleep(2000);
        WebElement footer = driver.findElement(By.tagName("footer"));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth',block:'center'})", footer);
        Thread.sleep(2000);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='3px solid #1DB954'", footer);
        Thread.sleep(1500);
        System.out.println("TEST 4: Scrolled to footer and highlighted green");
        Assert.assertTrue(footer.isDisplayed(), "Footer should be visible");
    }

    @Test(priority = 5)
    public void testSupportLinkHighlighted() throws InterruptedException {
        driver.get("https://www.spotify.com/premium/");
        Thread.sleep(2000);
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0)");
        Thread.sleep(1000);
        WebElement supportLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'support')] | //span[contains(text(),'Support')]")));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.outline='3px solid cyan'", supportLink);
        Thread.sleep(2000);
        System.out.println("TEST 5: Support link highlighted cyan");
        Assert.assertTrue(supportLink.isDisplayed(), "Support link should be visible");
    }

    @Test(priority = 6)
    public void testLoginButtonHighlightedAndClickable() throws InterruptedException {
        driver.get("https://www.spotify.com/premium/");
        Thread.sleep(2000);
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Log in')] | //a[contains(@href,'login')]")));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.background='yellow'", loginBtn);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='2px solid black'", loginBtn);
        Thread.sleep(2000);
        System.out.println("TEST 6: Login button highlighted yellow — clicking");
        loginBtn.click();
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().contains("spotify"),
                "Should redirect to Spotify login");
    }
}