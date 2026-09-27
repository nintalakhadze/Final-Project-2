package ge.tbc.testautomation;

import ge.tbc.testautomation.steps.HomePageSteps;
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

    private HomePageSteps homePageSteps;
    private OffersPageSteps offersPageSteps;

    @BeforeClass(alwaysRun = true)
    public void setUpSteps() {
        homePageSteps =
                new HomePageSteps(page);

        offersPageSteps =
                new OffersPageSteps(page);
    }

    @Test(priority = 1)
    @Description("Close cookie consent")
    public void closeCookie() {
        homePageSteps.acceptCookies();
    }

    @Test(
            priority = 2,
            dependsOnMethods = "closeCookie"
    )
    @Description("Open main menu")
    public void openMenu() {
        homePageSteps.hoverMainMenu();
    }

    @Test(
            priority = 3,
            dependsOnMethods = "openMenu"
    )
    @Description("Open Offers page")
    public void openOffersPage() {

        homePageSteps.clickOffersBtn();

        offersPageSteps
                .validateOffersPageUrl();
    }

    @Test(
            priority = 4,
            dependsOnMethods = "openOffersPage"
    )
    @Description("Open All Offers page")
    public void openAllOffers() {

        offersPageSteps
                .getAllOffers()
                .validateAllOffersPageUrl();
    }

    @Test(
            priority = 5,
            dependsOnMethods = "openAllOffers"
    )
    @Description("Clear default TBC Card filter")
    public void clearProductTypeFilter() {

        offersPageSteps
                .clearProductTypeFilter();
    }

    @Test(
            priority = 6,
            dependsOnMethods = "clearProductTypeFilter"
    )
    @Description(
            "Select Discount filter and capture network response"
    )
    public void selectDiscountFilter() {

        offersPageSteps
                .selectDiscountFilter();
    }

    @Test(
            priority = 7,
            dependsOnMethods = "selectDiscountFilter"
    )
    @Description(
            "Validate Discount offers network request and response"
    )
    public void validateOfferNetworkResponse() {

        offersPageSteps
                .validateOfferNetworkResponse();
    }

    @Test(
            priority = 8,
            dependsOnMethods = "validateOfferNetworkResponse"
    )
    @Description(
            "Validate Discount filter and resulting offers"
    )
    public void validateDiscountOffersDisplayed() {

        offersPageSteps
                .validateDiscountOffersDisplayed();
    }
}