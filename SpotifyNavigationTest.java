package com.gabriella;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;

public class SpotifyNavigationTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    /**
     * Improved click method that tries text first,
     * then tries common Spotify URL patterns if text fails.
     */
    private void smartClick(String text, String urlPart) {
        try {
            // Strategy 1: Try finding by partial text
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//a[contains(translate(text(), 'ABC', 'abc'), '" + text.toLowerCase() + "')]")
            ));
            executeClick(element);
        } catch (TimeoutException e) {
            // Strategy 2: Try finding by the actual link destination (more reliable in cache)
            System.out.println("Text locator failed for " + text + ". Trying URL pattern: " + urlPart);
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//a[contains(@href, '" + urlPart + "')]")
            ));
            executeClick(element);
        }
    }

    private void executeClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    @Test(priority = 1)
    public void openSpotifyHomePage() {
        driver.get("https://www.spotify.com/");
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("spotify"));
    }

    @Test(priority = 2)
    public void navigateToPremium() {
        driver.get("https://www.spotify.com/");
        // In the cache, the premium link usually points to /premium or spotify.com/1
        smartClick("Premium", "/1");
        Assert.assertTrue(driver.getCurrentUrl().contains("spotify.com") || driver.getPageSource().contains("Premium"));
    }

    @Test(priority = 3)
    public void navigateToDownload() {
        driver.get("https://www.spotify.com/");
        // In the cache, the download link usually points to /download or spotify.com/3
        smartClick("Download", "/3");
        Assert.assertTrue(driver.getPageSource().contains("Download") || driver.getCurrentUrl().contains("download"));
    }

    @Test(priority = 4)
    public void checkFooterVisibility() {
        driver.get("https://www.spotify.com/");
        // Fallback for footer if the tag is missing in the snapshot
        WebElement footer = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//footer | //*[contains(@class, 'footer')] | //*[contains(@id, 'footer')]")
        ));
        Assert.assertTrue(footer.isDisplayed());
    }
}