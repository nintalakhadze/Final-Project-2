package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.HomePage;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePageSteps {

    private final HomePage homePage;

    public HomePageSteps(Page page) {
        homePage = new HomePage(page);
    }

    public HomePageSteps acceptCookies() {
        if (homePage.cookie.isVisible()) {
            homePage.cookie.accept();

            assertThat(homePage.cookie.cookieBanner)
                    .isHidden();
        }

        return this;
    }

    public HomePageSteps openSideMenu() {
        homePage.sideMenu.open();

        assertThat(homePage.sideMenu.quickActions)
                .hasClass(
                        Pattern.compile(
                                ".*tbcx-pw-sticky-actions--open.*"
                        )
                );

        assertThat(homePage.sideMenu.currencyButton)
                .isVisible();

        return this;
    }

    public HomePageSteps clickCurrencyButton() {
        homePage.sideMenu.openCurrencyExchange();
        return this;
    }

    public HomePageSteps clickChatBtn() {
        homePage.sideMenu.openChat();
        return this;
    }

    public HomePageSteps hoverMainMenu() {
        homePage.header.hoverPersonalMenu();
        return this;
    }

    public HomePageSteps clickAddressBtn() {
        homePage.header.openAddresses();
        return this;
    }

    public HomePageSteps clickOffersBtn() {
        homePage.header.openOffers();
        return this;
    }
}