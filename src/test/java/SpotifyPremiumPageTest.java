package com.gabriella;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SpotifyPremiumPageTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://www.spotify.com/premium/");
        driver.manage().window().maximize();
    }

    @Test
    public void testPremiumPageLoads() {
        Assert.assertTrue(driver.getTitle().contains("Premium"));
    }

    @Test
    public void testPricingSectionVisible() {
        Assert.assertTrue(driver.getPageSource().contains("Premium"));
    }

    @Test
    public void testPlanCardsDisplayed() {
        Assert.assertTrue(driver.getPageSource().contains("Individual"));
    }

    @Test
    public void testGetPremiumButton() {
        Assert.assertTrue(driver.findElement(By.tagName("button")).isDisplayed());
    }

    @Test
    public void testPremiumFAQSection() {
        Assert.assertTrue(driver.getPageSource().contains("FAQ"));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
