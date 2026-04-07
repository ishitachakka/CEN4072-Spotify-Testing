package com.gabriella;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SpotifyNavigationTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://www.spotify.com/");
        driver.manage().window().maximize();
    }

    @Test
    public void testHomeNavigation() {
        Assert.assertTrue(driver.getTitle().contains("Spotify"));
    }

    @Test
    public void testPremiumNavigation() {
        driver.findElement(By.linkText("Premium")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("premium"));
    }

    @Test
    public void testSupportNavigation() {
        driver.findElement(By.linkText("Support")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("support"));
    }

    @Test
    public void testDownloadNavigation() {
        driver.findElement(By.linkText("Download")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("download"));
    }

    @Test
    public void testFooterLinks() {
        Assert.assertTrue(driver.findElement(By.tagName("footer")).isDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
