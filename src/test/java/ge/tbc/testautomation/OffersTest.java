package ge.tbc.testautomation;

import ge.tbc.testautomation.steps.OffersPageSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("TBC Digital")
@Feature("Offers")
@Story("Offers Network Validation")
public class OffersTest extends BaseTest {

    private OffersPageSteps offersPageSteps;

    @BeforeClass(alwaysRun = true)
    public void setUpSteps() {
        offersPageSteps = new OffersPageSteps(page);
    }

    @Test(
            priority = 1,
            description = "KAN-T12 | Open Offers page"
    )
    @Description(
            "Open the Offers page and validate that the correct page is displayed"
    )
    public void openOffersPage() {

        homePageSteps
                .hoverMainMenu()
                .clickOffersBtn();

        offersPageSteps
                .validateOffersPageUrl();
    }

    @Test(
            priority = 2,
            dependsOnMethods = "openOffersPage",
            description = "KAN-T12 | Open All Offers page"
    )
    @Description(
            "Open All Offers and validate that the All Offers page is displayed"
    )
    public void openAllOffers() {

        offersPageSteps
                .getAllOffers()
                .validateAllOffersPageUrl();
    }

    @Test(
            priority = 3,
            dependsOnMethods = "openAllOffers",
            description = "KAN-T12 | Select Discount filter"
    )
    @Description(
            "Select the Discount filter and capture the network response triggered by the filter"
    )
    public void selectDiscountFilter() {

        offersPageSteps
                .clearProductTypeFilter()
                .selectDiscountFilter();
    }

    @Test(
            priority = 4,
            dependsOnMethods = "selectDiscountFilter",
            description = "KAN-T12 | Validate Offers network request and response"
    )
    @Description(
            "Validate the Offers network endpoint, HTTP method, status code and request parameters"
    )
    public void validateOfferNetworkResponse() {

        offersPageSteps
                .validateOfferNetworkResponse();
    }

    @Test(
            priority = 5,
            dependsOnMethods = "validateOfferNetworkResponse",
            description = "KAN-T12 | Validate filtered Discount offers"
    )
    @Description(
            "Validate that the Discount filter is selected and filtered offers are displayed"
    )
    public void validateDiscountOffersDisplayed() {

        offersPageSteps
                .validateDiscountOffersDisplayed();
    }
}