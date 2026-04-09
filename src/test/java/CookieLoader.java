import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import java.io.*;
import java.util.*;

public class CookieLoader {

    public static void loadCookies(WebDriver driver) throws Exception {
        // Must visit Spotify first before adding cookies
        driver.get("https://open.spotify.com");
        Thread.sleep(2000);

        File cookieFile = new File("spotify_cookies.txt");
        if (!cookieFile.exists()) {
            System.out.println("WARNING: spotify_cookies.txt not found!");
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(cookieFile));
        String line;
        int count = 0;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\|\\|");
            if (parts.length >= 4) {
                try {
                    Cookie cookie = new Cookie(
                            parts[0],  // name
                            parts[1],  // value
                            parts[2],  // domain
                            parts[3],  // path
                            null       // expiry
                    );
                    driver.manage().addCookie(cookie);
                    count++;
                } catch (Exception e) {
                    // skip invalid cookies
                }
            }
        }
        br.close();
        System.out.println("Loaded " + count + " cookies");

        // Refresh to apply cookies
        driver.get("https://open.spotify.com");
        Thread.sleep(4000);
        System.out.println("After cookie load — URL: " + driver.getCurrentUrl());
    }
}