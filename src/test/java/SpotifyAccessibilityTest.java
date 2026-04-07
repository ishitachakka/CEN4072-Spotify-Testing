package com.gabriella;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.net.URL;

public class SpotifyAccessibilityTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testHomePageResponse200() throws Exception {
        URL url = new URL("https://www.spotify.com/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        Assert.assertEquals(connection.getResponseCode(), 200);
    }

    @Test
    public void testPremiumPageResponse200() throws Exception {
        URL url = new URL("https://www.spotify.com/premium/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        Assert.assertEquals(connection.getResponseCode(), 200);
    }

    @Test
    public void testDownloadPageResponse200() throws Exception {
        URL url = new URL("https://www.spotify.com/download/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        Assert.assertEquals(connection.getResponseCode(), 200);
    }

    @Test
    public void testPageTitleExists() {
        driver.get("https://www.spotify.com/");
        Assert.assertTrue(driver.getTitle().length() > 0);
    }

    @Test
    public void testURLsAreValid() {
        driver.get("https://www.spotify.com/");
        Assert.assertTrue(driver.getCurrentUrl().startsWith("https"));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
