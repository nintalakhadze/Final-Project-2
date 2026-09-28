package ge.tbc.testautomation.utils;

public class Constants {

    public static final String
            BASE_URL = "https://tbcbank.ge/ka",
            EXCHANGE_API_BASE_URL = "https://apigw.tbcbank.ge",
            CURRENCY_ZEPHYR_URL = "https://ninotalakhadzesworkspace-40004784.atlassian.net/jira/software/projects/KAN/apps/3feb7ced-1450-4676-aded-099c99bf534b/2baaeb69-15ac-4955-8eb6-e346aa1567aa#/v2/testCase/KAN-T10?projectId=10000",

    ADDRESS_URL = "https://tbcbank.ge/ka/atms&branches",
            OFFERS_URL = "https://tbcbank.ge/ka/offers",
            ALL_OFFERS_URL = "https://tbcbank.ge/ka/offers/all-offers?segment=All&filters=ProductType!TBCCard",

    OFFERS_ENDPOINT = "/api/v1/marketing/entries/offer",

    CASHBACK_FILTER = "OfferType:Cashback",
            INVALID_OFFER_FILTER = "OfferType:InvalidType",
            DISCOUNT_FILTER = "OfferType:Discount",

    OFFERS_LOCALE = "ka-GE",
            OFFERS_SEGMENT = "All",
            POST_METHOD = "POST",

    HELLO_MESSAGE = "გამარჯობა",
            REQUEST_END_CONVERSATION_MESSAGE = "საუბრის დასრულება",

    OFFER_RESPONSE_NOT_CAPTURED_MESSAGE =
            "Offer response was not captured",
            UNEXPECTED_OFFERS_ENDPOINT_MESSAGE =
                    "Unexpected offers endpoint: ",
            UNEXPECTED_HTTP_METHOD_MESSAGE =
                    "Unexpected HTTP method",
            UNEXPECTED_RESPONSE_STATUS_MESSAGE =
                    "Unexpected response status",
            OFFERS_REQUEST_BODY_NULL_MESSAGE =
                    "Offers request body is null",
            FILTER_FIELD_MISSING_MESSAGE =
                    "Filter field is missing from request",
            FILTER_FIELD_NOT_ARRAY_MESSAGE =
                    "Filter field is not an array",
            DISCOUNT_FILTER_NOT_SENT_MESSAGE =
                    "OfferType:Discount was not sent",
            UNEXPECTED_REQUEST_LOCALE_MESSAGE =
                    "Unexpected request locale",
            UNEXPECTED_REQUEST_SEGMENT_MESSAGE =
                    "Unexpected request segment",
            UNEXPECTED_PAGE_INDEX_MESSAGE =
                    "Unexpected page index",
            UNEXPECTED_PAGE_SIZE_MESSAGE =
                    "Unexpected page size",
            FAILED_TO_PARSE_OFFERS_REQUEST_MESSAGE =
                    "Failed to parse offers request body",
            NO_DISCOUNT_OFFERS_DISPLAYED_MESSAGE =
                    "No discount offers were displayed",

    API_RESPONSE_NULL_MESSAGE =
            "API response should not be null",
            API_RESPONSE_NULL_BEFORE_DESERIALIZATION_MESSAGE =
                    "API response should not be null before deserialization",
            DESERIALIZED_RESPONSE_NULL_MESSAGE =
                    "Deserialized response should not be null",
            PAGING_DETAILS_NULL_MESSAGE =
                    "Paging details should not be null",
            TOTAL_OFFERS_COUNT_INVALID_MESSAGE =
                    "Total offers count should be greater than zero",
            TOTAL_PAGES_INVALID_MESSAGE =
                    "Total pages should be greater than zero",
            OFFERS_LIST_NULL_MESSAGE =
                    "Offers list should not be null",
            OFFERS_LIST_EMPTY_MESSAGE =
                    "Offers list should not be empty",
            OFFERS_COUNT_EXCEEDS_PAGE_SIZE_MESSAGE =
                    "Offers count should not exceed requested page size",
            OFFER_TITLE_NULL_MESSAGE =
                    "Offer title should not be null",
            OFFER_TITLE_EMPTY_MESSAGE =
                    "Offer title should not be empty",
            OFFER_SLUG_NULL_MESSAGE =
                    "Offer slug should not be null",
            OFFER_SLUG_EMPTY_MESSAGE =
                    "Offer slug should not be empty",
            PARTNER_DATA_MISSING_MESSAGE =
                    "At least one offer should contain partner data",
            PARTNER_TITLE_NULL_MESSAGE =
                    "Partner title should not be null",
            PARTNER_TITLE_EMPTY_MESSAGE =
                    "Partner title should not be empty",
            PARTNER_SLUG_NULL_MESSAGE =
                    "Partner slug should not be null",
            PARTNER_SLUG_EMPTY_MESSAGE =
                    "Partner slug should not be empty",
            INVALID_OFFER_TOTAL_COUNT_MESSAGE =
                    "Total count should be zero for invalid offer type",
            INVALID_OFFER_TOTAL_PAGES_MESSAGE =
                    "Total pages should be zero for invalid offer type",
            NEXT_PAGE_SHOULD_NOT_EXIST_MESSAGE =
                    "Next page should not exist",
            INVALID_OFFER_LIST_NOT_EMPTY_MESSAGE =
                    "Offers list should be empty for invalid offer type",

    CHAT_HELP_QUESTION =
            "სხვა რამეში ხომ არ შემიძლია დაგეხმარო",
            CHAT_NO_RESPONSE = "არა",
            ENTER_KEY = "Enter",

    MEDALLIA_FRAME_NOT_FOUND_MESSAGE =
            "Medallia frame not found",
            CDM_LIST_NOT_LOADED_MESSAGE =
                    "CDM list is not loaded",
            CDM_NOT_FOUND_MESSAGE =
                    "CDM was not found after scrolling: ";

    public static final int
            OFFERS_PAGE_INDEX = 0,
            OFFERS_PAGE_SIZE = 12,
            SUCCESS_STATUS_CODE = 200,
            OFFERS_FILTER_TIMEOUT = 15_000,
            DEFAULT_UI_TIMEOUT = 15_000,
            SURVEY_TIMEOUT = 30_000,
            MAX_CDM_SCROLL_ATTEMPTS = 10;

    private Constants() {
    }
}