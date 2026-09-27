package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.CurrencyPage;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.utils.Constants.CURRENCY_PAGE_URL;

public class CurrencyPageSteps {

    Page page;
    CurrencyPage currencyPage;
    public CurrencyPageSteps(Page page){
        this.page=page;
        currencyPage = new CurrencyPage(page);
    }
    public CurrencyPageSteps validateCurrencyPageUrl() {

        assertThat(page).hasURL(
                Pattern.compile(".*/ka/valutis-kursi(?:/.*)?(?:\\?.*)?$")
        );

        return this;
    }

    public CurrencyPageSteps validateInputsAreVisible(){
        assertThat(currencyPage.sellAmountInput).isVisible();
        assertThat(currencyPage.buyAmountInput).isVisible();
        assertThat(currencyPage.sellCurrencyDropdown).isVisible();
        assertThat(currencyPage.buyCurrencyDropdown).isVisible();
        return this;
    }

    public CurrencyPageSteps selectSellCurrency(String currency){
        currencyPage.sellCurrencyDropdown.click();
        currencyPage.currencyOption(currency).click();
        return this;
    }
    public CurrencyPageSteps selectBuyCurrency(String currency){
        currencyPage.buyCurrencyDropdown.click();
        currencyPage.currencyOption(currency).click();
        return this;
    }

    public CurrencyPageSteps enterAmount(String amount){
        currencyPage.sellAmountInput.fill(amount);
        return this;
    }
    public CurrencyPageSteps validateConvertedAmountIsDisplayed() {
        assertThat(currencyPage.buyAmountInput)
                .hasValue(Pattern.compile("\\d+(\\.\\d+)?"));
        return this;
    }

    public CurrencyPageSteps validateConversionRate(
            String sellCurrency,
            String buyCurrency,
            double expectedRate) {

        DecimalFormat decimalFormat = new DecimalFormat(
                "0.####",
                DecimalFormatSymbols.getInstance(Locale.US)
        );

        String formattedRate = decimalFormat.format(expectedRate);

        String expectedRateText = String.format(
                "1 %s = %s %s",
                sellCurrency,
                formattedRate,
                buyCurrency
        );

        assertThat(currencyPage.conversionRate)
                .hasText(expectedRateText);

        return this;
    }

    public CurrencyPageSteps clickSwapCurrencyButton() {
        currencyPage.swapCurrencyButton.click();
        return this;
    }

    public CurrencyPageSteps validateSelectedCurrencies(String sellCurrency,
                                                        String buyCurrency){
        assertThat(currencyPage.selectedSellCurrency).hasText(sellCurrency);
        assertThat(currencyPage.selectedBuyCurrency).hasText(buyCurrency);
        return this;

    }


}
