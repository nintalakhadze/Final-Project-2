package ge.tbc.testautomation.api;

import ge.tbc.testautomation.models.ExchangeRateResponse;

import static ge.tbc.testautomation.utils.Constants.EXCHANGE_API_BASE_URL;
import static io.restassured.RestAssured.given;


public class ExchangeRateApi {

    public ExchangeRateResponse getExchangeRate(String iso1, String iso2) {
        return given()
                .baseUri(EXCHANGE_API_BASE_URL)
                .queryParam("Iso1", iso1)
                .queryParam("Iso2", iso2)
                .when()
                .get("/api/v1/exchangeRates/getExchangeRate")
                .then()
                .statusCode(200)
                .extract()
                .as(ExchangeRateResponse.class);
    }
}
