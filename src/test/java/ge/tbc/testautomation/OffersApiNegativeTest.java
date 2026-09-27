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

    @Test(priority = 1)
    @Description("Request offers with invalid offer type")
    public void requestInvalidOfferType() {
        offersApiSteps.requestInvalidOfferType();
    }

    @Test(
            priority = 2,
            dependsOnMethods = "requestInvalidOfferType"
    )
    @Description("Validate API response status for invalid offer type")
    public void validateStatusCode() {
        offersApiSteps.validateStatusCode();
    }

    @Test(
            priority = 3,
            dependsOnMethods = "validateStatusCode"
    )
    @Description("Deserialize invalid offer type response into POJO")
    public void deserializeResponse() {
        offersApiSteps.deserializeResponse();
    }

    @Test(
            priority = 4,
            dependsOnMethods = "deserializeResponse"
    )
    @Description("Validate empty response for invalid offer type")
    public void validateEmptyOffersResponse() {
        offersApiSteps.validateEmptyOffersResponse();
    }
}