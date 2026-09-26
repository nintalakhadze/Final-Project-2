package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.CookieComponent;
import ge.tbc.testautomation.components.SideMenuComponent;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePageSteps {
    Page page;
    SideMenuComponent sideMenuComponent;
    CookieComponent cookieComponent;

    public HomePageSteps(Page page) {
        this.page = page;
        sideMenuComponent = new SideMenuComponent(page);
        cookieComponent = new CookieComponent(page);
    }

    public HomePageSteps acceptCookies() {
        cookieComponent.acceptButton.click();
        assertThat(cookieComponent.cookieBanner).isHidden();
        return this;
    }

    public HomePageSteps openSideMenu() {
        sideMenuComponent.sideMenuButton.click();

        assertThat(sideMenuComponent.quickActions)
                .hasClass(Pattern.compile(".*tbcx-pw-sticky-actions--open.*"));

        assertThat(sideMenuComponent.currencyButton)
                .isVisible();
        return this;
    }

    public HomePageSteps clickCurrencyButton() {
        sideMenuComponent.currencyButton.click();
        assertThat(sideMenuComponent.currencyButton).isVisible();
        return this;
    }

    public HomePageSteps clickChatBtn(){
        sideMenuComponent.chatButton.click();
        return this;
    }

}
