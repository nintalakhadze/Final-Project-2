package ge.tbc.testautomation.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.assertions.LocatorAssertions;
import ge.tbc.testautomation.pages.OffersPage;
import org.testng.Assert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.utils.Constants.*;

public class OffersPageSteps {

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
                                .setTimeout(OFFERS_FILTER_TIMEOUT)
                );

        page.waitForResponse(
                response ->
                        response.url().contains(OFFERS_ENDPOINT)
                                && POST_METHOD.equals(
                                response.request().method()
                        ),
                () -> offersPage.clearProductTypeButton.click()
        );

        assertThat(offersPage.tbcCardCheckbox)
                .not()
                .isChecked(
                        new LocatorAssertions.IsCheckedOptions()
                                .setTimeout(OFFERS_FILTER_TIMEOUT)
                );

        return this;
    }

    public OffersPageSteps selectDiscountFilter() {

        offerResponse = page.waitForResponse(
                response ->
                        response.url().contains(OFFERS_ENDPOINT)
                                && POST_METHOD.equals(
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
                OFFER_RESPONSE_NOT_CAPTURED_MESSAGE
        );

        Assert.assertTrue(
                offerResponse.url().contains(OFFERS_ENDPOINT),
                UNEXPECTED_OFFERS_ENDPOINT_MESSAGE
                        + offerResponse.url()
        );

        Assert.assertEquals(
                offerResponse.request().method(),
                POST_METHOD,
                UNEXPECTED_HTTP_METHOD_MESSAGE
        );

        Assert.assertEquals(
                offerResponse.status(),
                SUCCESS_STATUS_CODE,
                UNEXPECTED_RESPONSE_STATUS_MESSAGE
        );

        String postData =
                offerResponse.request().postData();

        Assert.assertNotNull(
                postData,
                OFFERS_REQUEST_BODY_NULL_MESSAGE
        );

        try {

            JsonNode requestBody =
                    objectMapper.readTree(postData);

            JsonNode filters =
                    requestBody.get("filter");

            Assert.assertNotNull(
                    filters,
                    FILTER_FIELD_MISSING_MESSAGE
            );

            Assert.assertTrue(
                    filters.isArray(),
                    FILTER_FIELD_NOT_ARRAY_MESSAGE
            );

            Assert.assertTrue(
                    containsFilter(
                            filters,
                            DISCOUNT_FILTER
                    ),
                    DISCOUNT_FILTER_NOT_SENT_MESSAGE
            );

            Assert.assertEquals(
                    requestBody.get("locale").asText(),
                    OFFERS_LOCALE,
                    UNEXPECTED_REQUEST_LOCALE_MESSAGE
            );

            Assert.assertEquals(
                    requestBody.get("segment").asText(),
                    OFFERS_SEGMENT,
                    UNEXPECTED_REQUEST_SEGMENT_MESSAGE
            );

            Assert.assertEquals(
                    requestBody.get("pageIndex").asInt(),
                    OFFERS_PAGE_INDEX,
                    UNEXPECTED_PAGE_INDEX_MESSAGE
            );

            Assert.assertEquals(
                    requestBody.get("pageSize").asInt(),
                    OFFERS_PAGE_SIZE,
                    UNEXPECTED_PAGE_SIZE_MESSAGE
            );

        } catch (Exception e) {

            throw new AssertionError(
                    FAILED_TO_PARSE_OFFERS_REQUEST_MESSAGE,
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
                NO_DISCOUNT_OFFERS_DISPLAYED_MESSAGE
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