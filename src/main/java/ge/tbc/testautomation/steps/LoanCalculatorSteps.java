package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.components.HeaderComponent;
import ge.tbc.testautomation.pages.LoanCalculatorPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoanCalculatorSteps {

    private Page page;
    private HeaderComponent headerComponent;
    private LoanCalculatorPage loanCalculatorPage;

    public LoanCalculatorSteps(Page page) {
        this.page = page;
        this.headerComponent = new HeaderComponent(page);
        this.loanCalculatorPage = new LoanCalculatorPage(page);
    }

    public LoanCalculatorSteps clickMenuBtn(
            String expectedPersonalText
    ) {
        assertThat(headerComponent.personalMenuButton)
                .hasText(expectedPersonalText);

        headerComponent.personalMenuButton.hover();

        return this;
    }

    public LoanCalculatorSteps clickLoan(
            String expectedLoansText
    ) {
        assertThat(headerComponent.loansButton)
                .hasText(expectedLoansText);

        headerComponent.loansButton.click();

        return this;
    }

    public LoanCalculatorSteps openLoanCalculator(
            String expectedRequestLoanText
    ) {
        assertThat(loanCalculatorPage.requestLoanButton)
                .hasText(expectedRequestLoanText);

        Page calculatorPage = page.waitForPopup(
                () -> loanCalculatorPage.requestLoanButton.click()
        );

        calculatorPage.waitForLoadState();

        this.page = calculatorPage;
        this.headerComponent =
                new HeaderComponent(calculatorPage);
        this.loanCalculatorPage =
                new LoanCalculatorPage(calculatorPage);

        return this;
    }

    public LoanCalculatorSteps acceptCookies() {

        if (loanCalculatorPage.acceptCookiesButton.isVisible(
                new Locator.IsVisibleOptions()
                        .setTimeout(3000)
        )) {
            loanCalculatorPage.acceptCookiesButton.click();
        }

        return this;
    }

    public LoanCalculatorSteps selectLanguage(
            String locale
    ) {
        if (locale.equals("en")) {
            headerComponent.languageSwitcher.click();
            page.waitForURL("**/en");
        }

        return this;
    }

    public LoanCalculatorSteps selectCalculatorLanguage(
            String locale
    ) {
        if (locale.equals("en")) {

            loanCalculatorPage.languageDropdown.click();

            assertThat(
                    loanCalculatorPage.englishLanguageButton
            ).isVisible();

            page.waitForNavigation(
                    () -> loanCalculatorPage
                            .englishLanguageButton
                            .click()
            );
        }

        return this;
    }

    public LoanCalculatorSteps validateLocalization(
            String expectedTitle,
            String expectedAmountPlaceholder,
            String expectedPeriodPlaceholder
    ) {
        assertThat(loanCalculatorPage.calculatorTitle)
                .containsText(expectedTitle);

        assertThat(loanCalculatorPage.amountInput)
                .hasAttribute(
                        "placeholder",
                        expectedAmountPlaceholder
                );

        assertThat(loanCalculatorPage.periodInput)
                .hasAttribute(
                        "placeholder",
                        expectedPeriodPlaceholder
                );

        return this;
    }

    public LoanCalculatorSteps setAmount(
            String amount
    ) {
        loanCalculatorPage.amountInput.click();
        loanCalculatorPage.amountInput.fill("");
        loanCalculatorPage.amountInput
                .pressSequentially(amount);
        loanCalculatorPage.amountInput.press("Tab");

        String actualAmount =
                loanCalculatorPage.resultAmount
                        .innerText()
                        .replace(",", "");

        org.testng.Assert.assertEquals(
                actualAmount,
                amount
        );

        return this;
    }

    public LoanCalculatorSteps setPeriod(
            String period
    ) {
        loanCalculatorPage.periodInput.click();
        loanCalculatorPage.periodInput.fill("");
        loanCalculatorPage.periodInput
                .pressSequentially(period);
        loanCalculatorPage.periodInput.press("Tab");

        assertThat(loanCalculatorPage.resultPeriod)
                .hasText(period);

        return this;
    }

    public LoanCalculatorSteps validateMonthlyPaymentIsVisible() {

        assertThat(loanCalculatorPage.resultPayment)
                .isVisible();

        return this;
    }
}