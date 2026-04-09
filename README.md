# CEN4072 - Spotify Automation Testing

**Course:** CEN 4072 - Software Testing, Spring 2026  
**Instructor:** Dr. Deepa Devasenapathy  
**Team:** Ishita Chakkalakkal & Gabriella Vallar  

## Project Overview
Automated test suite for the Spotify web application using Selenium WebDriver and TestNG.

## Tech Stack
- Java 17
- Selenium WebDriver 4.18.1
- TestNG 7.9.0
- Maven
- ChromeDriver
- IntelliJ IDEA

## Test Classes
| Class | Author | Description |
|-------|--------|-------------|
| SpotifyHomePageTest | Ishita | Homepage scrolling, Music/Podcasts/Audiobooks/Following tabs |
| SpotifySearchTest | Ishita | Search Drake, add songs to playlist, like songs |
| SpotifyLoginPageTest | Ishita | Full login flow with password |
| SpotifySignupPageTest | Ishita | Signup form interactions |
| SpotifyNavigationTest | Gabriella | Premium, Support, Download navigation |
| SpotifyPremiumPageTest | Gabriella | Premium page elements and pricing |
| SpotifyDownloadPageTest | Gabriella | Download page platform links |
| SpotifyAccessibilityTest | Gabriella | HTTP 200 response checks |

## Setup Instructions
1. Clone the repository
2. Install ChromeDriver matching your Chrome version
3. Update chromedriver path in test files if needed
4. Run: `mvn test -Dsurefire.suiteXmlFiles=testng.xml`

## Running Tests
```bash
# Run full suite
mvn test -Dsurefire.suiteXmlFiles=testng.xml

# Run single class
mvn test -Dtest=SpotifyHomePageTest
```

## Automation Constraints Identified
- **Bot Protection** — Spotify detects Selenium login, solved with cookie-based sessions
- **Dynamic Elements** — JavaScript-rendered content, solved with WebDriverWait
- **Custom Scroll** — Spotify uses OverlayScrollbars, solved with scrollIntoView
- **Two-step Login** — Requires clicking "Log in with a password" before password field appears

## GitHub Repository
https://github.com/ishitachakka/CEN4072-Spotify-Testing
