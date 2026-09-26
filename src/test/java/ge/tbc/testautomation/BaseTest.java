package ge.tbc.testautomation;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.List;

import static ge.tbc.testautomation.utils.Constants.BASE_URL;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;

    @BeforeClass(alwaysRun = true)
    public void setupBrowser() {

        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(List.of("--start-maximized"))
        );

        browserContext = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(null)
        );

        page = browserContext.newPage();

        page.navigate(BASE_URL);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {

        if (browserContext != null) {
            browserContext.close();
        }

        if (browser != null) {
            browser.close();
        }

        if (playwright != null) {
            playwright.close();
        }
    }
}