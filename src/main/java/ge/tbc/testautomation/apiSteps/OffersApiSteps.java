package ge.tbc.testautomation.apiSteps;

import ge.tbc.testautomation.api.OffersApiClient;
import ge.tbc.testautomation.models.Offer;
import ge.tbc.testautomation.models.OfferRequest;
import ge.tbc.testautomation.models.OfferResponse;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

import static ge.tbc.testautomation.utils.Constants.*;

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
                API_RESPONSE_NULL_MESSAGE
        );

        Assert.assertEquals(
                response.statusCode(),
                SUCCESS_STATUS_CODE,
                UNEXPECTED_RESPONSE_STATUS_MESSAGE
        );

        return this;
    }

    public OffersApiSteps deserializeResponse() {
        Assert.assertNotNull(
                response,
                API_RESPONSE_NULL_BEFORE_DESERIALIZATION_MESSAGE
        );

        offerResponse = response.as(OfferResponse.class);

        Assert.assertNotNull(
                offerResponse,
                DESERIALIZED_RESPONSE_NULL_MESSAGE
        );

        return this;
    }

    public OffersApiSteps validateCashbackOffers() {
        Assert.assertNotNull(
                offerResponse.getPagingDetails(),
                PAGING_DETAILS_NULL_MESSAGE
        );

        Assert.assertEquals(
                offerResponse.getPagingDetails().getPageIndex(),
                OFFERS_PAGE_INDEX,
                UNEXPECTED_PAGE_INDEX_MESSAGE
        );

        Assert.assertEquals(
                offerResponse.getPagingDetails().getPageSize(),
                OFFERS_PAGE_SIZE,
                UNEXPECTED_PAGE_SIZE_MESSAGE
        );

        Assert.assertTrue(
                offerResponse.getPagingDetails().getTotalCount() > 0,
                TOTAL_OFFERS_COUNT_INVALID_MESSAGE
        );

        Assert.assertTrue(
                offerResponse.getPagingDetails().getTotalPages() > 0,
                TOTAL_PAGES_INVALID_MESSAGE
        );

        Assert.assertNotNull(
                offerResponse.getList(),
                OFFERS_LIST_NULL_MESSAGE
        );

        Assert.assertFalse(
                offerResponse.getList().isEmpty(),
                OFFERS_LIST_EMPTY_MESSAGE
        );

        Assert.assertTrue(
                offerResponse.getList().size() <= OFFERS_PAGE_SIZE,
                OFFERS_COUNT_EXCEEDS_PAGE_SIZE_MESSAGE
        );

        Offer firstOffer = offerResponse.getList().get(0);

        Assert.assertNotNull(
                firstOffer.getTitle(),
                OFFER_TITLE_NULL_MESSAGE
        );

        Assert.assertFalse(
                firstOffer.getTitle().isBlank(),
                OFFER_TITLE_EMPTY_MESSAGE
        );

        Assert.assertNotNull(
                firstOffer.getSlug(),
                OFFER_SLUG_NULL_MESSAGE
        );

        Assert.assertFalse(
                firstOffer.getSlug().isBlank(),
                OFFER_SLUG_EMPTY_MESSAGE
        );

        Offer offerWithPartner = offerResponse.getList()
                .stream()
                .filter(offer -> offer.getPartner() != null)
                .findFirst()
                .orElseThrow(
                        () -> new AssertionError(
                                PARTNER_DATA_MISSING_MESSAGE
                        )
                );

        Assert.assertNotNull(
                offerWithPartner.getPartner().getTitle(),
                PARTNER_TITLE_NULL_MESSAGE
        );

        Assert.assertFalse(
                offerWithPartner.getPartner().getTitle().isBlank(),
                PARTNER_TITLE_EMPTY_MESSAGE
        );

        Assert.assertNotNull(
                offerWithPartner.getPartner().getSlug(),
                PARTNER_SLUG_NULL_MESSAGE
        );

        Assert.assertFalse(
                offerWithPartner.getPartner().getSlug().isBlank(),
                PARTNER_SLUG_EMPTY_MESSAGE
        );

        return this;
    }

    public OffersApiSteps validateEmptyOffersResponse() {
        Assert.assertNotNull(
                offerResponse.getPagingDetails(),
                PAGING_DETAILS_NULL_MESSAGE
        );

        Assert.assertEquals(
                offerResponse.getPagingDetails().getPageIndex(),
                OFFERS_PAGE_INDEX,
                UNEXPECTED_PAGE_INDEX_MESSAGE
        );

        Assert.assertEquals(
                offerResponse.getPagingDetails().getPageSize(),
                OFFERS_PAGE_SIZE,
                UNEXPECTED_PAGE_SIZE_MESSAGE
        );

        Assert.assertEquals(
                offerResponse.getPagingDetails().getTotalCount(),
                0,
                INVALID_OFFER_TOTAL_COUNT_MESSAGE
        );

        Assert.assertEquals(
                offerResponse.getPagingDetails().getTotalPages(),
                0,
                INVALID_OFFER_TOTAL_PAGES_MESSAGE
        );

        Assert.assertFalse(
                offerResponse.getPagingDetails().isHasNextPage(),
                NEXT_PAGE_SHOULD_NOT_EXIST_MESSAGE
        );

        Assert.assertNotNull(
                offerResponse.getList(),
                OFFERS_LIST_NULL_MESSAGE
        );

        Assert.assertTrue(
                offerResponse.getList().isEmpty(),
                INVALID_OFFER_LIST_NOT_EMPTY_MESSAGE
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