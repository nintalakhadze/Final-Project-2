package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CurrencyPage {

    private final Page page;

    public final Locator sellAmountInput, buyAmountInput, sellCurrencyDropdown, buyCurrencyDropdown, selectedSellCurrency, selectedBuyCurrency, conversionRate, swapCurrencyButton;

    public CurrencyPage(Page page) {
        this.page = page;

        sellAmountInput = page.locator("#sell-amount");
        buyAmountInput = page.locator("#buy-amount");
        sellCurrencyDropdown = page.locator("app-currency-dropdown[formcontrolname='sellCurrency'] button");
        buyCurrencyDropdown = page.locator("app-currency-dropdown[formcontrolname='buyCurrency'] button");
        selectedSellCurrency = page.locator("app-currency-dropdown[formcontrolname='sellCurrency'] .currency-dropdown__selected");
        selectedBuyCurrency = page.locator("app-currency-dropdown[formcontrolname='buyCurrency'] .currency-dropdown__selected");
        conversionRate = page.locator(".exchange-rates-calculator__description");
        swapCurrencyButton = page.locator("button[icon='swap-outlined']");
    }


    public Locator currencyOption(String currency) {
        return page.locator(
                ".currency-dropdown__menu img[alt='" + currency + "']"
        ).locator("..");
    }
}