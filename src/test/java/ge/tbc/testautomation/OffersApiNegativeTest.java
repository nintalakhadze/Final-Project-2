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
@Story("Invalid Offer Type")
public class OffersApiNegativeTest {

    private OffersApiSteps offersApiSteps;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        offersApiSteps = new OffersApiSteps();
    }

    @Test(
            priority = 1,
            description = "KAN-T17 | Request offers with invalid offer type"
    )
    @Description(
            "Send an offers request using an invalid offer type and receive the API response"
    )
    public void requestInvalidOfferType() {

        offersApiSteps
                .requestInvalidOfferType();
    }

    @Test(
            priority = 2,
            dependsOnMethods = "requestInvalidOfferType",
            description = "KAN-T17 | Validate API response status"
    )
    @Description(
            "Validate the API response status for a request with an invalid offer type"
    )
    public void validateStatusCode() {

        offersApiSteps
                .validateStatusCode();
    }

    @Test(
            priority = 3,
            dependsOnMethods = "validateStatusCode",
            description = "KAN-T17 | Deserialize API response into POJO"
    )
    @Description(
            "Deserialize the invalid offer type API response into the corresponding POJO"
    )
    public void deserializeResponse() {

        offersApiSteps
                .deserializeResponse();
    }

    @Test(
            priority = 4,
            dependsOnMethods = "deserializeResponse",
            description = "KAN-T17 | Validate empty offers response"
    )
    @Description(
            "Validate that an invalid offer type returns an empty offers list and the expected paging data"
    )
    public void validateEmptyOffersResponse() {

        offersApiSteps
                .validateEmptyOffersResponse();
    }
}