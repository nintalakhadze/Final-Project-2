package ge.tbc.testautomation.apiSteps;

import ge.tbc.testautomation.api.OffersApiClient;
import ge.tbc.testautomation.models.Offer;
import ge.tbc.testautomation.models.OfferRequest;
import ge.tbc.testautomation.models.OfferResponse;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

import static ge.tbc.testautomation.utils.Constants.CASHBACK_FILTER;
import static ge.tbc.testautomation.utils.Constants.INVALID_OFFER_FILTER;
import static ge.tbc.testautomation.utils.Constants.OFFERS_LOCALE;
import static ge.tbc.testautomation.utils.Constants.OFFERS_PAGE_INDEX;
import static ge.tbc.testautomation.utils.Constants.OFFERS_PAGE_SIZE;
import static ge.tbc.testautomation.utils.Constants.OFFERS_SEGMENT;

public class OffersApiSteps {

    private final OffersApiClient offersApiClient;

    private Response response;
    private OfferResponse offerResponse;

    public OffersApiSteps() {
        this.offersApiClient = new OffersApiClient();
    }

    public OffersApiSteps requestCashbackOffers() {
        response = offersApiClient.getOffers(
                createOfferRequest(CASHBACK_FILTER)
        );

        return this;
    }

    public OffersApiSteps requestInvalidOfferType() {
        response = offersApiClient.getOffers(
                createOfferRequest(INVALID_OFFER_FILTER)
        );

        return this;
    }

    public OffersApiSteps validateStatusCode() {
        Assert.assertNotNull(
                response,
                "API response should not be null"
        );

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Unexpected status code"
        );

        return this;
    }

    public OffersApiSteps deserializeResponse() {
        Assert.assertNotNull(
                response,
                "API response should not be null before deserialization"
        );

        offerResponse = response.as(OfferResponse.class);

        Assert.assertNotNull(
                offerResponse,
                "Deserialized response should not be null"
        );

        return this;
    }

    public OffersApiSteps validateCashbackOffers() {
        Assert.assertNotNull(
                offerResponse.getPagingDetails(),
                "Paging details should not be null"
        );

        Assert.assertEquals(
                offerResponse.getPagingDetails().getPageIndex(),
                OFFERS_PAGE_INDEX,
                "Unexpected page index"
        );

        Assert.assertEquals(
                offerResponse.getPagingDetails().getPageSize(),
                OFFERS_PAGE_SIZE,
                "Unexpected page size"
        );

        Assert.assertTrue(
                offerResponse.getPagingDetails().getTotalCount() > 0,
                "Total offers count should be greater than zero"
        );

        Assert.assertTrue(
                offerResponse.getPagingDetails().getTotalPages() > 0,
                "Total pages should be greater than zero"
        );

        Assert.assertNotNull(
                offerResponse.getList(),
                "Offers list should not be null"
        );

        Assert.assertFalse(
                offerResponse.getList().isEmpty(),
                "Offers list should not be empty"
        );

        Assert.assertTrue(
                offerResponse.getList().size() <= OFFERS_PAGE_SIZE,
                "Offers count should not exceed requested page size"
        );

        Offer firstOffer = offerResponse.getList().get(0);

        Assert.assertNotNull(
                firstOffer.getTitle(),
                "Offer title should not be null"
        );

        Assert.assertFalse(
                firstOffer.getTitle().isBlank(),
                "Offer title should not be empty"
        );

        Assert.assertNotNull(
                firstOffer.getSlug(),
                "Offer slug should not be null"
        );

        Assert.assertFalse(
                firstOffer.getSlug().isBlank(),
                "Offer slug should not be empty"
        );

        Offer offerWithPartner = offerResponse.getList()
                .stream()
                .filter(offer -> offer.getPartner() != null)
                .findFirst()
                .orElseThrow(
                        () -> new AssertionError(
                                "At least one offer should contain partner data"
                        )
                );

        Assert.assertNotNull(
                offerWithPartner.getPartner().getTitle(),
                "Partner title should not be null"
        );

        Assert.assertFalse(
                offerWithPartner.getPartner().getTitle().isBlank(),
                "Partner title should not be empty"
        );

        Assert.assertNotNull(
                offerWithPartner.getPartner().getSlug(),
                "Partner slug should not be null"
        );

        Assert.assertFalse(
                offerWithPartner.getPartner().getSlug().isBlank(),
                "Partner slug should not be empty"
        );

        return this;
    }

    public OffersApiSteps validateEmptyOffersResponse() {
        Assert.assertNotNull(
                offerResponse.getPagingDetails(),
                "Paging details should not be null"
        );

        Assert.assertEquals(
                offerResponse.getPagingDetails().getPageIndex(),
                OFFERS_PAGE_INDEX,
                "Unexpected page index"
        );

        Assert.assertEquals(
                offerResponse.getPagingDetails().getPageSize(),
                OFFERS_PAGE_SIZE,
                "Unexpected page size"
        );

        Assert.assertEquals(
                offerResponse.getPagingDetails().getTotalCount(),
                0,
                "Total count should be zero for invalid offer type"
        );

        Assert.assertEquals(
                offerResponse.getPagingDetails().getTotalPages(),
                0,
                "Total pages should be zero for invalid offer type"
        );

        Assert.assertFalse(
                offerResponse.getPagingDetails().isHasNextPage(),
                "Next page should not exist"
        );

        Assert.assertNotNull(
                offerResponse.getList(),
                "Offers list should not be null"
        );

        Assert.assertTrue(
                offerResponse.getList().isEmpty(),
                "Offers list should be empty for invalid offer type"
        );

        return this;
    }

    private OfferRequest createOfferRequest(String filter) {
        return new OfferRequest(
                List.of(filter),
                OFFERS_LOCALE,
                OFFERS_SEGMENT,
                OFFERS_PAGE_INDEX,
                OFFERS_PAGE_SIZE
        );
    }
}