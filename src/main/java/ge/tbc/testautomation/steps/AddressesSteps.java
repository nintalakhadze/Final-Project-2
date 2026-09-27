package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.models.CdmData;
import ge.tbc.testautomation.pages.AddressesPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.utils.Constants.ADDRESS_URL;
import static ge.tbc.testautomation.utils.Constants.CURRENCY_PAGE_URL;

public class AddressesSteps {
    Page page;
    AddressesPage addressesPage;
    public AddressesSteps(Page page) {

        this.page = page;
        addressesPage = new AddressesPage(page);
    }

    public AddressesSteps validateAddressPageUrl(){
        assertThat(page).hasURL(ADDRESS_URL);
        return this;
    }

    public AddressesSteps clickCDMs(){
        addressesPage.cdmTab.click();
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

        for (int i = 0; i < 10; i++) {

            Locator cdmItem = addressesPage.cdmByAddress(address);

            if (cdmItem.count() > 0) {
                return this;
            }

            addressesPage.cdmItems.last()
                    .scrollIntoViewIfNeeded();
        }

        throw new AssertionError(
                "CDM was not found after scrolling: " + address
        );
    }

}
