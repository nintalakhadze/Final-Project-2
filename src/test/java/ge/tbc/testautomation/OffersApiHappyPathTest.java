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

    @Test(priority = 1)
    @Description("Request cashback offers")
    public void requestCashbackOffers() {
        offersApiSteps.requestCashbackOffers();
    }

    @Test(
            priority = 2,
            dependsOnMethods = "requestCashbackOffers"
    )
    @Description("Validate successful API response status")
    public void validateStatusCode() {
        offersApiSteps.validateStatusCode();
    }

    @Test(
            priority = 3,
            dependsOnMethods = "validateStatusCode"
    )
    @Description("Deserialize API response into POJO")
    public void deserializeResponse() {
        offersApiSteps.deserializeResponse();
    }

    @Test(
            priority = 4,
            dependsOnMethods = "deserializeResponse"
    )
    @Description("Validate cashback offers and nested response data")
    public void validateCashbackOffers() {
        offersApiSteps.validateCashbackOffers();
    }
}