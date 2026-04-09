import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import java.io.*;
import java.util.Set;

public class SpotifyLoginHelper {

    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver",
                "/Users/ishitachakkalakkal/Documents/Software Testing/SeleniumDrivers/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.setAcceptInsecureCerts(true);

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Open Spotify login
        driver.get("https://open.spotify.com");
        System.out.println("=== LOG IN MANUALLY IN THE BROWSER ===");
        System.out.println("1. Click Log in");
        System.out.println("2. Enter your email and password");
        System.out.println("3. Complete any captcha if needed");
        System.out.println("4. Wait until you see the Spotify homepage");
        System.out.println("5. Then press ENTER here to save cookies...");

        // Wait for you to log in manually
        new java.util.Scanner(System.in).nextLine();

        // Save all cookies to a file
        Set<Cookie> cookies = driver.manage().getCookies();
        FileWriter fw = new FileWriter("spotify_cookies.txt");
        for (Cookie cookie : cookies) {
            fw.write(cookie.getName() + "||" +
                    cookie.getValue() + "||" +
                    cookie.getDomain() + "||" +
                    cookie.getPath() + "||" +
                    (cookie.getExpiry() != null ? cookie.getExpiry().getTime() : "0") + "\n");
        }
        fw.close();
        System.out.println("Cookies saved! " + cookies.size() + " cookies written.");
        driver.quit();
    }
}