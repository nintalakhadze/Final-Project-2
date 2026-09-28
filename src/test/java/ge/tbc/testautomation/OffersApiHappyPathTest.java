package ge.tbc.testautomation;

import ge.tbc.testautomation.apiSteps.OffersApiSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("TBC Digital")
@Feature("Offers API")
@Story("Cashback Offers Happy Path")
public class OffersApiHappyPathTest {

    private OffersApiSteps offersApiSteps;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        offersApiSteps = new OffersApiSteps();
    }

    @Test(
            priority = 1,
            description = "KAN-T16 | Request cashback offers"
    )
    @Description(
            "Send a request for cashback offers and receive the API response"
    )
    public void requestCashbackOffers() {

        offersApiSteps
                .requestCashbackOffers();
    }

    @Test(
            priority = 2,
            dependsOnMethods = "requestCashbackOffers",
            description = "KAN-T16 | Validate successful API response status"
    )
    @Description(
            "Validate that the cashback offers API returns a successful response status"
    )
    public void validateStatusCode() {

        offersApiSteps
                .validateStatusCode();
    }

    @Test(
            priority = 3,
            dependsOnMethods = "validateStatusCode",
            description = "KAN-T16 | Deserialize API response into POJO"
    )
    @Description(
            "Deserialize the cashback offers API response into the corresponding POJO"
    )
    public void deserializeResponse() {

        offersApiSteps
                .deserializeResponse();
    }

    @Test(
            priority = 4,
            dependsOnMethods = "deserializeResponse",
            description = "KAN-T16 | Validate cashback offers response data"
    )
    @Description(
            "Validate cashback offers, paging information and nested partner data from the API response"
    )
    public void validateCashbackOffers() {

        offersApiSteps
                .validateCashbackOffers();
    }
}