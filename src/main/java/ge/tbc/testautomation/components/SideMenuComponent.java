package ge.tbc.testautomation.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SideMenuComponent {

    public final Locator quickActions;
    public final Locator sideMenuButton;
    public final Locator currencyButton;
    public final Locator chatButton;

    public SideMenuComponent(Page page) {
        quickActions = page.locator(".tbcx-pw-sticky-actions");

        sideMenuButton = quickActions.locator(
                "button:has(tbcx-icon:text('kebab-menu-vertical-outlined'))"
        );

        currencyButton = quickActions.locator(
                "a[href='/ka/valutis-kursi'] button"
        );

        chatButton = quickActions.locator(
                "button:has(tbcx-icon:text('chat-dots-filled'))"
        );
    }

    public void open() {
        sideMenuButton.click();
    }

    public void openCurrencyExchange() {
        currencyButton.click();
    }

    public void openChat() {
        chatButton.click();
    }
}