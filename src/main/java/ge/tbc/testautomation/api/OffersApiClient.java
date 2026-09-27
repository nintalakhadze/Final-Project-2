package ge.tbc.testautomation.api;

import ge.tbc.testautomation.models.OfferRequest;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static ge.tbc.testautomation.utils.Constants.EXCHANGE_API_BASE_URL;
import static ge.tbc.testautomation.utils.Constants.OFFERS_API_ENDPOINT;
import static io.restassured.RestAssured.given;

public class OffersApiClient {

    public Response getOffers(OfferRequest request) {

        return given()
                .baseUri(EXCHANGE_API_BASE_URL)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .filter(new AllureRestAssured())
                .body(request)
                .when()
                .post(OFFERS_API_ENDPOINT);
    }
}