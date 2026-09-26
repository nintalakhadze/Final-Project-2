package ge.tbc.testautomation.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SideMenuComponent {
    public final Locator quickActions, sideMenuButton, currencyButton, chatButton;

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
}
