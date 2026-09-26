package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoanCalculatorPage {
    public final Locator requestLoanButton, amountInput,
            periodInput,
            resultPayment,
            resultAmount,
            resultPeriod,
            acceptCookiesButton,
            calculatorTitle,
            languageDropdown,
            englishLanguageButton;

    public LoanCalculatorPage(Page page) {

        requestLoanButton = page.locator(
                "tbcx-pw-cta-banner-section " +
                        "a[href='https://tbcbank.onelink.me/YiId/ularj23r'] button"
        );
        amountInput = page.locator(
                "#standard-calculator-amount"
        );

        periodInput = page.locator(
                "#standard-calculator-period"
        );

        resultPayment = page.locator(
                "#standard-calculator-result-payment"
        );

        resultAmount = page.locator(
                "#standard-calculator-result-amount"
        );

        resultPeriod = page.locator(
                "#standard-calculator-result-period"
        );
        acceptCookiesButton = page.locator(
                ".cookie-side-window.active #acceptAllCookies"
        );
        calculatorTitle = page.locator(
                "#standard-calculator .title"
        ).first();
        languageDropdown = page.locator(
                "#language-dropdown .selected"
        );

        englishLanguageButton = page.locator(
                "#language-dropdown button[name='culture'][value='en']"
        );
    }

}
