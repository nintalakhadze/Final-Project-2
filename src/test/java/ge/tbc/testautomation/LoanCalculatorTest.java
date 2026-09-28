package ge.tbc.testautomation;

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
    private final String expectedLoansText;
    private final String expectedRequestLoanText;
    private final String expectedCalculatorTitle;
    private final String expectedAmountPlaceholder;
    private final String expectedPeriodPlaceholder;

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
        this.expectedLoansText = expectedLoansText;
        this.expectedRequestLoanText = expectedRequestLoanText;
        this.expectedCalculatorTitle = expectedCalculatorTitle;
        this.expectedAmountPlaceholder = expectedAmountPlaceholder;
        this.expectedPeriodPlaceholder = expectedPeriodPlaceholder;
    }

    @BeforeClass(alwaysRun = true)
    public void setUpSteps() {
        loanCalculatorSteps = new LoanCalculatorSteps(page);
    }

    @BeforeMethod(alwaysRun = true)
    public void addLocaleToAllure() {
        Allure.parameter("Locale", locale);
    }

    @Test(
            priority = 1,
            description = "KAN-T13 | Select website language"
    )
    @Description(
            "Select the website language based on the provided locale"
    )
    public void selectLanguage() {
        loanCalculatorSteps.selectLanguage(locale);
    }

    @Test(
            priority = 2,
            dependsOnMethods = "selectLanguage",
            description = "KAN-T13 | Open Loans page"
    )
    @Description(
            "Open the Loans page and validate the localized navigation"
    )
    public void openLoansPage() {

        homePageSteps.hoverMainMenu();

        loanCalculatorSteps
                .clickLoan(expectedLoansText);
    }

    @Test(
            priority = 3,
            dependsOnMethods = "openLoansPage",
            description = "KAN-T13 | Open Loan Calculator"
    )
    @Description(
            "Open the Loan Calculator and accept the calculator cookie consent"
    )
    public void openLoanCalculator() {

        loanCalculatorSteps.openLoanCalculator(
                expectedRequestLoanText
        );

        loanCalculatorSteps.acceptCookies();
    }

    @Test(
            priority = 4,
            dependsOnMethods = "openLoanCalculator",
            description = "KAN-T13 | Select calculator language"
    )
    @Description(
            "Select the Loan Calculator language based on the provided locale"
    )
    public void selectCalculatorLanguage() {

        loanCalculatorSteps.selectCalculatorLanguage(
                locale
        );
    }

    @Test(
            priority = 5,
            dependsOnMethods = "selectCalculatorLanguage",
            description = "KAN-T13 | Validate calculator localization"
    )
    @Description(
            "Validate the localized calculator title, loan amount and loan period fields"
    )
    public void validateCalculatorLocalization() {

        loanCalculatorSteps.validateLocalization(
                expectedCalculatorTitle,
                expectedAmountPlaceholder,
                expectedPeriodPlaceholder
        );
    }

    @Test(
            priority = 6,
            dependsOnMethods = "validateCalculatorLocalization",
            description = "KAN-T13 | Set loan amount"
    )
    @Description(
            "Enter 5000 as the loan amount"
    )
    public void setLoanAmount() {

        loanCalculatorSteps.setAmount("5000");
    }

    @Test(
            priority = 7,
            dependsOnMethods = "setLoanAmount",
            description = "KAN-T13 | Set loan period"
    )
    @Description(
            "Enter 24 as the loan period"
    )
    public void setLoanPeriod() {

        loanCalculatorSteps.setPeriod("24");
    }

    @Test(
            priority = 8,
            dependsOnMethods = "setLoanPeriod",
            description = "KAN-T13 | Validate monthly payment"
    )
    @Description(
            "Validate that the calculated monthly payment is displayed"
    )
    public void validateMonthlyPayment() {

        loanCalculatorSteps
                .validateMonthlyPaymentIsVisible();
    }
}