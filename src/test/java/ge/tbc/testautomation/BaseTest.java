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
        System.out.println(
                "START CLASS: " +
                        getClass().getSimpleName() +
                        " | INSTANCE: " +
                        System.identityHashCode(this) +
                        " | THREAD: " +
                        Thread.currentThread().getName()
        );

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

        context.addInitScript("""
    (() => {
        const style = document.createElement('style');
        style.textContent = `
            #MDigitalLightboxWrapper,
            #kampyleFormContainer,
            iframe[title="Feedback Survey"] {
                display: none !important;
                pointer-events: none !important;
            }
        `;

        const addStyle = () => {
            if (document.head && !document.getElementById('disable-medallia-survey')) {
                style.id = 'disable-medallia-survey';
                document.head.appendChild(style);
            }
        };

        if (document.head) {
            addStyle();
        } else {
            document.addEventListener('DOMContentLoaded', addStyle);
        }
    })();
""");

        page = context.newPage();
        page.navigate(BASE_URL);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownBrowser() {
        System.out.println(
                "END CLASS: " +
                        getClass().getSimpleName() +
                        " | INSTANCE: " +
                        System.identityHashCode(this) +
                        " | THREAD: " +
                        Thread.currentThread().getName()
        );

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