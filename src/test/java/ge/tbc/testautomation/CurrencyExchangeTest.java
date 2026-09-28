package ge.tbc.testautomation;

import ge.tbc.testautomation.apiSteps.ExchangeRateApiSteps;
import ge.tbc.testautomation.models.ExchangeRateResponse;
import ge.tbc.testautomation.steps.CurrencyPageSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.utils.Constants.CURRENCY_ZEPHYR_URL;

@Epic("TBC Digital")
@Feature("Currency Exchange")
@Story("Currency conversion and API-UI consistency")
@Link(
        name = "ვალუტის კონვერტაცია და გაცვლითი კურსის შესაბამისობა",
        url = CURRENCY_ZEPHYR_URL
)
public class CurrencyExchangeTest extends BaseTest {

    private CurrencyPageSteps currencyPageSteps;
    private ExchangeRateApiSteps exchangeRateApiSteps;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        currencyPageSteps = new CurrencyPageSteps(page);
        exchangeRateApiSteps = new ExchangeRateApiSteps();
    }

    @Test(
            priority = 1,
            description = "KAN-T10 | Open Currency Exchange page"
    )
    @Description(
            "Zephyr Step 1: ვალუტის კურსის გვერდის გახსნა"
    )
    public void openCurrencyExchangePageAndValidatePage() {

        homePageSteps
                .openSideMenu()
                .clickCurrencyButton();

        currencyPageSteps
                .validateCurrencyPageUrl()
                .validateInputsAreVisible();
    }

    @Test(
            priority = 2,
            dependsOnMethods = "openCurrencyExchangePageAndValidatePage",
            description = "KAN-T10 | Select EUR and USD currencies"
    )
    @Description(
            "Zephyr Step 2: გასაყიდ ვალუტად EUR-ის და საყიდელ ვალუტად USD-ის არჩევა"
    )
    public void selectSellAndBuyCurrencies() {

        currencyPageSteps
                .selectSellCurrency("EUR")
                .selectBuyCurrency("USD")
                .validateSelectedCurrencies("EUR", "USD");
    }

    @Test(
            priority = 3,
            dependsOnMethods = "selectSellAndBuyCurrencies",
            description = "KAN-T10 | Enter EUR sell amount"
    )
    @Description(
            "Zephyr Step 3: გასაყიდი თანხის შეყვანა და კონვერტირებული თანხის ვალიდაცია"
    )
    public void enterSellAmount() {

        currencyPageSteps
                .enterAmount("150")
                .validateConvertedAmountIsDisplayed();
    }

    @Test(
            priority = 4,
            dependsOnMethods = "enterSellAmount",
            description = "KAN-T10 | Validate EUR/USD API-UI consistency"
    )
    @Description(
            "Zephyr Step 4: EUR/USD ვალუტებისა და გაცვლითი კურსის API-სთან შედარება"
    )
    public void validateEurToUsdExchangeRate() {

        ExchangeRateResponse exchangeRate =
                exchangeRateApiSteps.getExchangeRate("EUR", "USD");

        currencyPageSteps
                .validateSelectedCurrencies(
                        exchangeRate.getIso1(),
                        exchangeRate.getIso2()
                )
                .validateConversionRate(
                        exchangeRate.getIso1(),
                        exchangeRate.getIso2(),
                        exchangeRate.getBuyRate()
                );
    }

    @Test(
            priority = 5,
            dependsOnMethods = "validateEurToUsdExchangeRate",
            description = "KAN-T10 | Swap currencies"
    )
    @Description(
            "Zephyr Step 5: ვალუტების ადგილების შეცვლა და ახალი მიმართულების ვალიდაცია"
    )
    public void swapCurrencies() {

        currencyPageSteps
                .clickSwapCurrencyButton()
                .validateSelectedCurrencies("USD", "EUR");
    }

    @Test(
            priority = 6,
            dependsOnMethods = "swapCurrencies",
            description = "KAN-T10 | Enter new amount after currency swap"
    )
    @Description(
            "Zephyr Step 6: ვალუტების ადგილების შეცვლის შემდეგ ახალი თანხის შეყვანა"
    )
    public void enterNewAmountAfterSwap() {

        currencyPageSteps
                .enterAmount("120")
                .validateConvertedAmountIsDisplayed();
    }

    @Test(
            priority = 7,
            dependsOnMethods = "enterNewAmountAfterSwap",
            description = "KAN-T10 | Validate USD/EUR API-UI consistency"
    )
    @Description(
            "Zephyr Step 7: USD/EUR ვალუტებისა და გაცვლითი კურსის API-სთან შედარება"
    )
    public void validateUsdToEurExchangeRate() {

        ExchangeRateResponse exchangeRate =
                exchangeRateApiSteps.getExchangeRate("USD", "EUR");

        currencyPageSteps
                .validateSelectedCurrencies(
                        exchangeRate.getIso1(),
                        exchangeRate.getIso2()
                )
                .validateConversionRate(
                        exchangeRate.getIso1(),
                        exchangeRate.getIso2(),
                        exchangeRate.getBuyRate()
                );
    }
}