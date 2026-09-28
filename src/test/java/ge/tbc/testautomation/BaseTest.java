package ge.tbc.testautomation;

import com.microsoft.playwright.*;
import ge.tbc.testautomation.steps.HomePageSteps;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.List;

import static ge.tbc.testautomation.utils.Constants.BASE_URL;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    protected HomePageSteps homePageSteps;

    @BeforeClass(alwaysRun = true)
    public void setUpBrowser() {
        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(true)
        );

        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(1920, 1080)
                        .setPermissions(List.of("geolocation"))
                        .setGeolocation(41.7151, 44.8271)
        );

        page = context.newPage();
        page.navigate(BASE_URL);

        homePageSteps = new HomePageSteps(page);
        homePageSteps.acceptCookies();
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