package ge.tbc.testautomation.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CookieComponent {

    public final Locator cookieBanner;
    public final Locator acceptButton;

    public CookieComponent(Page page) {
        cookieBanner = page.locator(".tbcx-pw-cookie-consent");

        acceptButton = cookieBanner.getByRole(
                AriaRole.BUTTON,
                new Locator.GetByRoleOptions()
                        .setName("თანხმობა")
                        .setExact(true)
        );
    }

    public boolean isVisible() {
        return cookieBanner.isVisible();
    }

    public void accept() {
        acceptButton.click();
    }
}