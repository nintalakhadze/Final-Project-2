package ge.tbc.testautomation;

import ge.tbc.testautomation.models.CdmData;
import ge.tbc.testautomation.steps.AddressesSteps;
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
        addressesSteps = new AddressesSteps(page);
    }

    @Test(
            priority = 1,
            description = "KAN-T15 | Open Addresses page"
    )
    @Description(
            "Open the Addresses page and validate that the correct page is displayed"
    )
    public void openAddressPage() {

        homePageSteps
                .hoverMainMenu()
                .clickAddressBtn();

        addressesSteps
                .validateAddressPageUrl();
    }

    @Test(
            priority = 2,
            dependsOnMethods = "openAddressPage",
            description = "KAN-T15 | Select CDMs"
    )
    @Description(
            "Select the CDM tab and validate that the CDM list is displayed"
    )
    public void chooseCDMs() {

        addressesSteps
                .clickCDMs();
    }

    @Test(
            priority = 3,
            dependsOnMethods = "chooseCDMs",
            description = "KAN-T15 | Validate CDM data from database"
    )
    @Description(
            "Validate that the CDM address, working hours and currencies displayed in the UI match the database data"
    )
    public void validateCdmData() {

        addressesSteps
                .validateCdmData(cdmData);
    }
}