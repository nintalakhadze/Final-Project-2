package ge.tbc.testautomation;

import ge.tbc.testautomation.steps.HomePageSteps;
import ge.tbc.testautomation.steps.LoanCalculatorSteps;
import ge.tbc.testautomation.utils.DataProviders;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

@Epic("TBC Digital")
@Feature("Localization")
@Story("Loan Calculator Localization")
public class LoanCalculatorTest extends BaseTest {

    private final String locale;
    private final String expectedPersonalText;
    private final String expectedLoansText;
    private final String expectedRequestLoanText;
    private final String expectedCalculatorTitle;
    private final String expectedAmountPlaceholder;
    private final String expectedPeriodPlaceholder;

    private HomePageSteps homePageSteps;
    private LoanCalculatorSteps loanCalculatorSteps;
    @Factory(
            dataProvider = "localizationData",
            dataProviderClass = DataProviders.class
    )
    public LoanCalculatorTest(
            String locale,
            String expectedPersonalText,
            String expectedLoansText,
            String expectedRequestLoanText,
            String expectedCalculatorTitle,
            String expectedAmountPlaceholder,
            String expectedPeriodPlaceholder
    ) {
        this.locale = locale;
        this.expectedPersonalText = expectedPersonalText;
        this.expectedLoansText = expectedLoansText;
        this.expectedRequestLoanText = expectedRequestLoanText;
        this.expectedCalculatorTitle = expectedCalculatorTitle;
        this.expectedAmountPlaceholder = expectedAmountPlaceholder;
        this.expectedPeriodPlaceholder = expectedPeriodPlaceholder;
    }

    @BeforeClass(alwaysRun = true)
    public void setUpSteps() {
        homePageSteps = new HomePageSteps(page);
        loanCalculatorSteps = new LoanCalculatorSteps(page);
    }

    @BeforeMethod(alwaysRun = true)
    public void addLocaleToAllure() {
        Allure.parameter("Locale", locale);
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
    @Description("Select website language")
    public void selectLanguage() {
        loanCalculatorSteps.selectLanguage(locale);
    }

    @Test(priority = 3, dependsOnMethods = "selectLanguage")
    @Description("Open personal menu")
    public void openMenu() {
        homePageSteps.hoverMainMenu();
    }

    @Test(
            priority = 4,
            dependsOnMethods = "openMenu"
    )
    @Description("Open Loans page")
    public void openLoansPage() {
        loanCalculatorSteps.clickLoan(expectedLoansText);
    }

    @Test(
            priority = 5,
            dependsOnMethods = "openLoansPage"
    )
    @Description("Open Loan Calculator")
    public void openLoanCalculator() {
        loanCalculatorSteps.openLoanCalculator(
                expectedRequestLoanText
        );
    }

    @Test(
            priority = 6,
            dependsOnMethods = "openLoanCalculator"
    )
    @Description("Close calculator cookie consent")
    public void closeCalculatorCookie() {
        loanCalculatorSteps.acceptCookies();
    }

    @Test(
            priority = 7,
            dependsOnMethods = "closeCalculatorCookie"
    )
    @Description("Select calculator language")
    public void selectCalculatorLanguage() {
        loanCalculatorSteps.selectCalculatorLanguage(
                locale
        );
    }

    @Test(
            priority = 8,
            dependsOnMethods = "selectCalculatorLanguage"
    )
    @Description("Validate calculator localization")
    public void validateCalculatorLocalization() {
        loanCalculatorSteps.validateLocalization(
                expectedCalculatorTitle,
                expectedAmountPlaceholder,
                expectedPeriodPlaceholder
        );
    }

    @Test(
            priority = 9,
            dependsOnMethods = "validateCalculatorLocalization"
    )
    @Description("Set loan amount")
    public void setLoanAmount() {
        loanCalculatorSteps.setAmount("5000");
    }

    @Test(
            priority = 10,
            dependsOnMethods = "setLoanAmount"
    )
    @Description("Set loan period")
    public void setLoanPeriod() {
        loanCalculatorSteps.setPeriod("24");
    }

    @Test(
            priority = 11,
            dependsOnMethods = "setLoanPeriod"
    )
    @Description("Validate monthly payment")
    public void validateMonthlyPayment() {
        loanCalculatorSteps
                .validateMonthlyPaymentIsVisible();
    }
}