package com.gabriella;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class SpotifyDownloadPageTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications", "--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        if (driver != null) {
            System.out.println("Closing Download Test in 3 seconds...");
            Thread.sleep(3000);
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void testDownloadPageNavigation() throws InterruptedException {
        driver.get("https://www.spotify.com/download/");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getPageSource().contains("Download"), "Download page not reached.");
        System.out.println("Test 1: Download page successfully loaded.");
    }

    @Test(priority = 2)
    public void testHighlightMicrosoftStoreButton() throws InterruptedException {
        // Targets the large Microsoft Store button seen in your screenshot
        WebElement storeBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(), 'Microsoft Store')] | //img[contains(@alt, 'Microsoft')]")
        ));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", storeBtn);
        Thread.sleep(1500);
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='5px solid cyan'", storeBtn);

        System.out.println("Test 2: Microsoft Store button highlighted.");
        Thread.sleep(2000);
        Assert.assertTrue(storeBtn.isDisplayed());
    }

    @Test(priority = 3)
    public void testDirectDownloadLink() throws InterruptedException {
        // Targets the smaller "Download directly from Spotify" link below the main button
        WebElement directLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(text(), 'directly from Spotify')]")
        ));

        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid pink'", directLink);
        System.out.println("Test 3: Direct download link highlighted.");
        Thread.sleep(2000);
        Assert.assertTrue(directLink.isDisplayed());
    }

    @Test(priority = 4)
    public void testLoginLinkInteractivity() throws InterruptedException {
        // Targets the 'Log in' button in the top right of your screenshot
        WebElement loginLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(text(), 'Log in')] | //button[contains(text(), 'Log in')]")
        ));

        // Action: Scroll to top and Highlight in Yellow
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", loginLink);
        Thread.sleep(1000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.background='yellow'", loginLink);
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='2px solid black'", loginLink);

        System.out.println("Test 4: Log in link found and highlighted yellow.");
        Thread.sleep(2000);

        Assert.assertTrue(loginLink.isDisplayed(), "Log in link should be visible to the user.");
    }

    @Test(priority = 5)
    public void testHeaderBrandingIntegrity() throws InterruptedException {
        // Targets the Spotify logo in the top left corner of your screenshot
        WebElement logo = driver.findElement(By.xpath("//header//a[contains(@href, 'spotify')] | //svg[contains(@aria-hidden, 'true')]"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", logo);
        Thread.sleep(1000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid white'", logo);

        System.out.println("Test 5: Top branding confirmed.");
        Assert.assertTrue(logo.isDisplayed());
    }
}