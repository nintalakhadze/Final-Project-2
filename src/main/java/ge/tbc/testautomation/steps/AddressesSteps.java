package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import ge.tbc.testautomation.models.CdmData;
import ge.tbc.testautomation.pages.AddressesPage;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.utils.Constants.*;

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

        assertThat(addressesPage.cdmTab)
                .isVisible();

        addressesPage.cdmTab.click();

        assertThat(addressesPage.cdmTab)
                .hasClass(
                        Pattern.compile(".*active.*")
                );

        return this;
    }

    public AddressesSteps validateCdmData(
            CdmData cdmData
    ) {

        scrollUntilCdmIsLoaded(
                cdmData.getAddress()
        );

        Locator cdmItem =
                addressesPage.cdmByAddress(
                        cdmData.getAddress()
                );

        assertThat(cdmItem).isVisible();

        assertThat(
                addressesPage.cdmAddress(cdmItem)
        ).hasText(
                cdmData.getAddress()
        );

        Locator description =
                addressesPage.cdmDescription(cdmItem);

        for (String workingHour :
                cdmData.getWorkingHours().split(";")) {

            assertThat(description)
                    .containsText(
                            workingHour.trim()
                    );
        }

        Locator currencies =
                addressesPage.cdmCurrencies(cdmItem);

        for (String currency :
                cdmData.getCurrencies().split(",")) {

            assertThat(currencies)
                    .containsText(
                            currency.trim()
                    );
        }

        return this;
    }

    public AddressesSteps scrollUntilCdmIsLoaded(
            String address
    ) {

        assertThat(addressesPage.cdmItems.first())
                .isVisible(
                        new LocatorAssertions.IsVisibleOptions()
                                .setTimeout(DEFAULT_UI_TIMEOUT)
                );

        for (int i = 0;
             i < MAX_CDM_SCROLL_ATTEMPTS;
             i++) {

            Locator cdmItem =
                    addressesPage.cdmByAddress(address);

            if (cdmItem.count() > 0) {
                return this;
            }

            int itemCount =
                    addressesPage.cdmItems.count();

            if (itemCount == 0) {
                throw new AssertionError(
                        CDM_LIST_NOT_LOADED_MESSAGE
                );
            }

            addressesPage.cdmItems
                    .nth(itemCount - 1)
                    .scrollIntoViewIfNeeded();
        }

        throw new AssertionError(
                CDM_NOT_FOUND_MESSAGE + address
        );
    }
}