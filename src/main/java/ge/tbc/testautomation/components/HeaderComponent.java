package ge.tbc.testautomation.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class HeaderComponent {
    public final Locator personalMenuButton,loansButton,languageSwitcher,addressesButton;

    public HeaderComponent(Page page){
        personalMenuButton = page.locator(
                "tbcx-pw-navigation.show-desktop-only " +
                        "a:is([href='/ka'], [href='/en']) " +
                        "button.tbcx-pw-navigation-item__link"
        );
        loansButton = page.locator(
                ".show-tablet-up " +
                        "a:is([href='/ka/loans'], [href='/en/loans']) " +
                        "button.tbcx-pw-mega-menu-sub-item--isTitle"
        );
        languageSwitcher = page.locator(
                "tbcx-lang-switcher.show-tablet-up .tbcx-language-select__field"
        );
        addressesButton = page.getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions()
                        .setName("მისამართები")
                        .setExact(true)
        );
    }
}
