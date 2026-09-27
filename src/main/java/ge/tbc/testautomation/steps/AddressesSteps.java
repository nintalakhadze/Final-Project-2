package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.models.CdmData;
import ge.tbc.testautomation.pages.AddressesPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.utils.Constants.ADDRESS_URL;

public class AddressesSteps {

    private final Page page;
    private final AddressesPage addressesPage;

    public AddressesSteps(Page page) {
        this.page = page;
        this.addressesPage = new AddressesPage(page);
    }

    public AddressesSteps validateAddressPageUrl() {
        assertThat(page).hasURL(ADDRESS_URL);
        return this;
    }

    public AddressesSteps clickCDMs() {

        assertThat(addressesPage.cdmTab).isVisible();


        addressesPage.cdmTab.click();

        assertThat(addressesPage.cdmTab)
                .hasClass(
                        java.util.regex.Pattern.compile(".*active.*")
                );

        return this;
    }

    public AddressesSteps validateCdmData(CdmData cdmData) {

        scrollUntilCdmIsLoaded(cdmData.getAddress());

        Locator cdmItem =
                addressesPage.cdmByAddress(cdmData.getAddress());

        assertThat(cdmItem).isVisible();

        Locator address = cdmItem.locator(
                ".tbcx-pw-atm-branches-section__list-item-title"
        );

        assertThat(address)
                .hasText(cdmData.getAddress());

        Locator description = cdmItem.locator(
                ".tbcx-pw-atm-branches-section__list-item-description"
        );

        for (String workingHour :
                cdmData.getWorkingHours().split(";")) {

            assertThat(description)
                    .containsText(workingHour.trim());
        }

        Locator currencies = cdmItem.locator(
                ".tbcx-pw-atm-branches-section__list-item-currencies"
        );

        for (String currency :
                cdmData.getCurrencies().split(",")) {

            assertThat(currencies)
                    .containsText(currency.trim());
        }

        return this;
    }

    public AddressesSteps scrollUntilCdmIsLoaded(String address) {

        assertThat(addressesPage.cdmItems.first())
                .isVisible(
                        new com.microsoft.playwright.assertions
                                .LocatorAssertions.IsVisibleOptions()
                                .setTimeout(15_000)
                );

        for (int i = 0; i < 10; i++) {

            Locator cdmItem =
                    addressesPage.cdmByAddress(address);

            if (cdmItem.count() > 0) {
                return this;
            }

            int itemCount =
                    addressesPage.cdmItems.count();

            if (itemCount == 0) {
                throw new AssertionError(
                        "CDM list is not loaded"
                );
            }

            addressesPage.cdmItems
                    .nth(itemCount - 1)
                    .scrollIntoViewIfNeeded();
        }

        throw new AssertionError(
                "CDM was not found after scrolling: " + address
        );
    }
}