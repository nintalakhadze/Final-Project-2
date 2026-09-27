package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class OffersPage {

    public final Locator allOffersButton,
            clearProductTypeButton,
            tbcCardCheckbox,
            discountFilter,
            discountCheckbox,
            offerCards;

    public OffersPage(Page page) {

        allOffersButton = page.locator(
                "a[href*='/offers/all-offers']"
        ).first();

        Locator productTypeTitle = page.locator(
                ".filter__title"
        ).filter(
                new Locator.FilterOptions()
                        .setHasText("პროდუქტის ტიპი")
        );

        Locator productTypeGroup = page.locator(
                "app-marketing-filter-group"
        ).filter(
                new Locator.FilterOptions()
                        .setHas(productTypeTitle)
        );

        clearProductTypeButton = productTypeGroup.locator(
                "button.filter__button"
        );

        tbcCardCheckbox = page.locator(
                "app-marketing-filter-item"
        ).filter(
                new Locator.FilterOptions()
                        .setHasText("თიბისი ბარათი")
        ).locator(
                "input[type='checkbox']"
        );

        discountFilter = page.locator(
                "app-marketing-filter-item"
        ).filter(
                new Locator.FilterOptions()
                        .setHasText("ფასდაკლება")
        ).locator(
                "button.filter-item"
        );

        discountCheckbox = discountFilter.locator(
                "input[type='checkbox']"
        );

        offerCards = page.locator(
                "a[href*='/offers/all-offers/'] tbcx-pw-card"
        );
    }
}