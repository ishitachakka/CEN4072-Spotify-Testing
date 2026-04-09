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

public class SpotifyPremiumPageTest {
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
            System.out.println("Demo complete. Closing browser...");
            Thread.sleep(3000);
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void testPageLoadAndHeading() throws InterruptedException {
        driver.get("https://www.spotify.com/premium/");
        WebElement mainHeading = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h1 | //h2[contains(@class, 'heading')]")
        ));

        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='5px solid yellow'", mainHeading);
        System.out.println("Test 1: Heading highlighted. Pausing for 3 seconds...");
        Thread.sleep(3000); // Increased pause
    }

    @Test(priority = 2)
    public void testFeatureContentPresence() throws InterruptedException {
        String bodyText = driver.findElement(By.tagName("body")).getText();
        boolean hasPremiumContent = bodyText.contains("Premium") || bodyText.contains("Music");

        System.out.println("Test 2: Content verified. Pausing for 3 seconds...");
        Thread.sleep(3000); // Added pause for visibility
        Assert.assertTrue(hasPremiumContent);
    }

    @Test(priority = 3)
    public void testScrollToFooterLinks() throws InterruptedException {
        WebElement footer = driver.findElement(By.tagName("footer"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", footer);

        System.out.println("Test 3: Scrolled to footer. Pausing for 3 seconds...");
        Thread.sleep(3000); // Increased pause after scroll
        Assert.assertTrue(footer.isDisplayed());
    }

    @Test(priority = 4)
    public void testTopNavSupportLink() throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        Thread.sleep(1500); // Wait for scroll to finish

        WebElement supportLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href, 'support')] | //span[contains(text(), 'Support')]")
        ));

        ((JavascriptExecutor) driver).executeScript("arguments[0].style.outline='3px solid cyan'", supportLink);
        System.out.println("Test 4: Support link highlighted. Pausing for 3 seconds...");
        Thread.sleep(3000); // Increased pause
    }

    @Test(priority = 5)
    public void testLoginRedirectAndClose() throws InterruptedException {
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Log in')] | //a[contains(@href, 'login')]")
        ));

        ((JavascriptExecutor) driver).executeScript("arguments[0].style.background='yellow'", loginBtn);
        System.out.println("Test 5: Final highlight before login click. Pausing 3 seconds...");
        Thread.sleep(3000); // Final pause so they see the button we are clicking

        loginBtn.click();

        // Final verify and wait before AfterClass kicks in
        Thread.sleep(2000);
        System.out.println("Final Test: Redirecting. Task Complete.");
    }
}