package ge.tbc.testautomation.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.assertions.LocatorAssertions;
import ge.tbc.testautomation.pages.OffersPage;
import org.testng.Assert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.utils.Constants.ALL_OFFERS_URL;
import static ge.tbc.testautomation.utils.Constants.OFFERS_URL;

public class OffersPageSteps {

    private static final String OFFERS_ENDPOINT =
            "/api/v1/marketing/entries/offer";

    private static final String DISCOUNT_FILTER =
            "OfferType:Discount";

    private final Page page;
    private final OffersPage offersPage;
    private final ObjectMapper objectMapper;

    private Response offerResponse;

    public OffersPageSteps(Page page) {
        this.page = page;
        this.offersPage = new OffersPage(page);
        this.objectMapper = new ObjectMapper();
    }

    public OffersPageSteps validateOffersPageUrl() {
        assertThat(page).hasURL(OFFERS_URL);
        return this;
    }

    public OffersPageSteps getAllOffers() {
        offersPage.allOffersButton.click();
        return this;
    }

    public OffersPageSteps validateAllOffersPageUrl() {
        assertThat(page).hasURL(ALL_OFFERS_URL);
        return this;
    }

    public OffersPageSteps clearProductTypeFilter() {

        assertThat(offersPage.tbcCardCheckbox)
                .isChecked(
                        new LocatorAssertions.IsCheckedOptions()
                                .setTimeout(15_000)
                );

        page.waitForResponse(
                response ->
                        response.url().contains(OFFERS_ENDPOINT)
                                && "POST".equals(
                                response.request().method()
                        ),
                () -> offersPage.clearProductTypeButton.click()
        );

        assertThat(offersPage.tbcCardCheckbox)
                .not()
                .isChecked(
                        new LocatorAssertions.IsCheckedOptions()
                                .setTimeout(15_000)
                );

        return this;
    }

    public OffersPageSteps selectDiscountFilter() {

        offerResponse = page.waitForResponse(
                response ->
                        response.url().contains(OFFERS_ENDPOINT)
                                && "POST".equals(
                                response.request().method()
                        )
                                && hasDiscountFilter(
                                response.request().postData()
                        ),

                () -> offersPage.discountFilter.click()
        );

        return this;
    }

    public OffersPageSteps validateOfferNetworkResponse() {

        Assert.assertNotNull(
                offerResponse,
                "Offer response was not captured"
        );

        Assert.assertTrue(
                offerResponse.url().contains(OFFERS_ENDPOINT),
                "Unexpected offers endpoint: "
                        + offerResponse.url()
        );

        Assert.assertEquals(
                offerResponse.request().method(),
                "POST",
                "Unexpected HTTP method"
        );

        Assert.assertEquals(
                offerResponse.status(),
                200,
                "Unexpected response status"
        );

        String postData =
                offerResponse.request().postData();

        Assert.assertNotNull(
                postData,
                "Offers request body is null"
        );

        try {

            JsonNode requestBody =
                    objectMapper.readTree(postData);

            JsonNode filters =
                    requestBody.get("filter");

            Assert.assertNotNull(
                    filters,
                    "Filter field is missing from request"
            );

            Assert.assertTrue(
                    filters.isArray(),
                    "Filter field is not an array"
            );

            Assert.assertTrue(
                    containsFilter(
                            filters,
                            DISCOUNT_FILTER
                    ),
                    "OfferType:Discount was not sent"
            );

            Assert.assertEquals(
                    requestBody.get("locale").asText(),
                    "ka-GE",
                    "Unexpected request locale"
            );

            Assert.assertEquals(
                    requestBody.get("segment").asText(),
                    "All",
                    "Unexpected request segment"
            );

            Assert.assertEquals(
                    requestBody.get("pageIndex").asInt(),
                    0,
                    "Unexpected page index"
            );

            Assert.assertEquals(
                    requestBody.get("pageSize").asInt(),
                    12,
                    "Unexpected page size"
            );

        } catch (Exception e) {

            throw new AssertionError(
                    "Failed to parse offers request body",
                    e
            );
        }

        return this;
    }

    public OffersPageSteps validateDiscountOffersDisplayed() {

        assertThat(
                offersPage.discountCheckbox
        ).isChecked();

        assertThat(
                offersPage.offerCards.first()
        ).isVisible();

        int offersCount =
                offersPage.offerCards.count();

        Assert.assertTrue(
                offersCount > 0,
                "No discount offers were displayed"
        );

        return this;
    }

    private boolean hasDiscountFilter(String postData) {

        if (postData == null) {
            return false;
        }

        try {

            JsonNode requestBody =
                    objectMapper.readTree(postData);

            JsonNode filters =
                    requestBody.get("filter");

            return filters != null
                    && filters.isArray()
                    && containsFilter(
                    filters,
                    DISCOUNT_FILTER
            );

        } catch (Exception e) {
            return false;
        }
    }

    private boolean containsFilter(
            JsonNode filters,
            String expectedFilter
    ) {

        for (JsonNode filter : filters) {

            if (expectedFilter.equals(
                    filter.asText()
            )) {
                return true;
            }
        }

        return false;
    }
}