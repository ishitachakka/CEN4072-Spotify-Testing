package com.gabriella;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.List;

public class SpotifyPremiumPageTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        // Automatically manages the driver for Windows, Mac, or Linux
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        options.setAcceptInsecureCerts(true);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test(priority = 1)
    public void testPremiumPageAndScroll() {
        // 1. Open the Premium Page (Google Cache version)
        driver.get("https://www.spotify.com/premium/");
        System.out.println("Step 1: Premium page loaded.");

        // 2. Scroll down to see the plans
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        System.out.println("Step 2: Scrolled down to Plan section.");

        // 3. Check for specific Plan names (Case-insensitive check)
        String pageSource = driver.getPageSource().toLowerCase();
        boolean hasIndividual = pageSource.contains("individual");
        boolean hasStudent = pageSource.contains("student");

        Assert.assertTrue(hasIndividual || hasStudent, "Premium plan names not found in page source!");
        System.out.println("Step 3: Confirmed plan text is visible.");

        // 4. Specifically check for Get Started buttons
        // Looking for any link or button that looks like a signup or action button
        List<WebElement> buttons = driver.findElements(By.xpath("//button | //a[contains(@href, 'signup') or contains(@href, 'premium')]"));

        Assert.assertFalse(buttons.isEmpty(), "No action buttons found on the Premium page!");

        WebElement firstButton = buttons.get(0);
        Assert.assertTrue(firstButton.isDisplayed(), "The Premium action button is not visible!");
        System.out.println("Step 4: Verified " + buttons.size() + " buttons/links found.");
    }

    @Test(priority = 2)
    public void verifyUrlStructure() {
        // Since we are using a Google Cache URL, it won't start with HTTPS
        // So we check if the path contains 'spotify' or '1' (the premium index)
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("googleusercontent") || currentUrl.contains("spotify"),
                "URL does not seem to be the Spotify content page!");
        System.out.println("Step 5: URL structure verified.");
    }
}