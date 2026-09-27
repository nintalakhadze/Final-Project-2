package ge.tbc.testautomation;

import ge.tbc.testautomation.models.CdmData;
import ge.tbc.testautomation.steps.AddressesSteps;
import ge.tbc.testautomation.steps.HomePageSteps;
import ge.tbc.testautomation.utils.DataProviders;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

@Epic("TBC Digital")
@Feature("CDM Locations")
@Story("Database Driven CDM Validation")
public class CdmDataDrivenTest extends BaseTest {

    private final CdmData cdmData;

    private HomePageSteps homePageSteps;
    private AddressesSteps addressesSteps;

    @Factory(
            dataProvider = "cdmData",
            dataProviderClass = DataProviders.class
    )
    public CdmDataDrivenTest(CdmData cdmData) {
        this.cdmData = cdmData;
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        homePageSteps = new HomePageSteps(page);
        addressesSteps = new AddressesSteps(page);
    }

    @Test(priority = 1)
    @Description("Close cookie consent")
    public void closeCookie() {
        homePageSteps.acceptCookies();
    }

    @Test(priority = 2, dependsOnMethods = "closeCookie")
    @Description("Open personal menu")
    public void openMenu() {
        homePageSteps.clickMainMenuBtn();
    }

    @Test(priority = 3, dependsOnMethods = "openMenu")
    @Description("Open addresses page")
    public void openAddressPage() {
        homePageSteps.clickAddressBtn();
        addressesSteps.validateAddressPageUrl();
    }

    @Test(priority = 4, dependsOnMethods = "openAddressPage")
    @Description("Select CDMs")
    public void chooseCDMs() {
        addressesSteps.clickCDMs();
    }

    @Test(priority = 5, dependsOnMethods = "chooseCDMs")
    @Description("Validate CDM data from database")
    public void validateCdmData() {
        addressesSteps.validateCdmData(cdmData);
    }
}