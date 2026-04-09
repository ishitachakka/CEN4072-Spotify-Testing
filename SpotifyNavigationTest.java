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
import java.util.Set;

public class SpotifyNavigationTest {

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
            Thread.sleep(3000);
            driver.quit();
        }
    }

    // --- NEW: Helper to handle those pesky new tabs/windows ---
    public void switchToNewWindow() {
        String mainWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                System.out.println("Focus shifted to the new window/tab.");
            }
        }
    }

    public void clickButtonByText(String text) throws InterruptedException {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + text.toLowerCase() + "')]")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        Thread.sleep(1500);
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid #1DB954'", element);
        Thread.sleep(1000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    @Test(priority = 1)
    public void testPremiumJourney() throws InterruptedException {
        driver.get("https://www.spotify.com/");
        clickButtonByText("Premium");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getPageSource().contains("Premium"));
    }

    @Test(priority = 2)
    public void testDownloadJourney() throws InterruptedException {
        driver.get("https://www.spotify.com/");
        clickButtonByText("Download");
        Thread.sleep(2000);
        Assert.assertTrue(driver.getPageSource().contains("Download"));
    }

    @Test(priority = 3)
    public void testSupportJourney() throws InterruptedException {
        driver.get("https://www.spotify.com/");
        clickButtonByText("Support");

        // Check if Support opened in a new tab
        Thread.sleep(2000);
        switchToNewWindow();

        Assert.assertTrue(driver.getPageSource().contains("Support") || driver.getTitle().contains("Support"));
    }

    @Test(priority = 4)
    public void testLegalLinkJourney() throws InterruptedException {
        // Go back to main page first
        driver.get("https://www.spotify.com/");
        clickButtonByText("Legal");

        Thread.sleep(2000);
        switchToNewWindow(); // If Legal opened a new tab, jump to it!

        System.out.println("Checking content on: " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getPageSource().contains("Legal") || driver.getCurrentUrl().contains("legal"));
    }

    @Test(priority = 5)
    public void testLogoHomeRedirect() throws InterruptedException {
        driver.get("https://www.spotify.com/premium/");
        Thread.sleep(2000);
        WebElement logo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//header//a[1] | //a[contains(@aria-label, 'Spotify')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logo);
        Thread.sleep(2000);
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("spotify"));
    }
}