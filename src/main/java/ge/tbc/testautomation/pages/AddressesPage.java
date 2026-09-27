package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

public class AddressesPage {
    private final Page page;


    public final Locator cdmTab,cdmItems;

    public AddressesPage(Page page) {
        this.page = page;


        cdmTab = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName(
                                Pattern.compile(
                                        "^(თანხის მიმღები|CDMs)$"
                                )
                        )
        );
        cdmItems = page.locator(
                ".tbcx-pw-atm-branches-section__list--desktop-view " +
                        "app-atm-branches-section-list-item"
        );
    }

    public Locator cdmByAddress(String address) {
        return cdmItems.filter(
                new Locator.FilterOptions()
                        .setHas(
                                page.locator(
                                        ".tbcx-pw-atm-branches-section__list-item-title",
                                        new Page.LocatorOptions()
                                                .setHasText(address)
                                )
                        )
        ).first();
    }

}
