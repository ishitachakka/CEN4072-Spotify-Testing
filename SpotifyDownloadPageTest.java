package com.gabriella;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SpotifyDownloadPageTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://www.spotify.com/download/");
        driver.manage().window().maximize();
    }

    @Test
    public void testDownloadPageLoads() {
        Assert.assertTrue(driver.getTitle().contains("Download"));
    }

    @Test
    public void testWindowsDownloadLink() {
        Assert.assertTrue(driver.getPageSource().contains("Download"));
    }

    @Test
    public void testMacDownloadLink() {
        Assert.assertTrue(driver.getPageSource().contains("Mac"));
    }

    @Test
    public void testMobileDownloadLinks() {
        Assert.assertTrue(driver.getPageSource().contains("Mobile"));
    }

    @Test
    public void testInstallSpotifyButton() {
        Assert.assertTrue(driver.findElement(By.tagName("a")).isDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
