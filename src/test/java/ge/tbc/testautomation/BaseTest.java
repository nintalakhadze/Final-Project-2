package ge.tbc.testautomation;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.List;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    protected static final String BASE_URL =
            "https://www.tbcbank.ge";

    @BeforeClass(alwaysRun = true)
    public void setUpBrowser() {

        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(
                                List.of(
                                        "--start-maximized"
                                )
                        )
        );

        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(null)
                        .setPermissions(
                                List.of("geolocation")
                        )
                        .setGeolocation(
                                41.7151,
                                44.8271
                        )
        );

        page = context.newPage();

        page.navigate(BASE_URL);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownBrowser() {

        if (context != null) {
            context.close();
        }

        if (browser != null) {
            browser.close();
        }

        if (playwright != null) {
            playwright.close();
        }
    }
}