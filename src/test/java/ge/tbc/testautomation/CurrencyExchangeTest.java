package ge.tbc.testautomation;

import ge.tbc.testautomation.apiSteps.ExchangeRateApiSteps;
import ge.tbc.testautomation.models.ExchangeRateResponse;
import ge.tbc.testautomation.steps.CurrencyPageSteps;
import ge.tbc.testautomation.steps.HomePageSteps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Story;

import static ge.tbc.testautomation.utils.Constants.CURRENCY_ZEPHYR_URL;

@Epic("TBC Digital")
@Feature("Currency Exchange")
@Story("Currency conversion and API-UI consistency")
@Link(
        name = "ვალუტის კონვერტაცია და გაცვლითი კურსის შესაბამისობა",
        url = CURRENCY_ZEPHYR_URL
)
public class CurrencyExchangeTest extends BaseTest {

    HomePageSteps homePageSteps;
    CurrencyPageSteps currencyPageSteps;
    ExchangeRateApiSteps exchangeRateApiSteps;

    @BeforeClass
    public void setUp() {
        homePageSteps = new HomePageSteps(page);
        currencyPageSteps = new CurrencyPageSteps(page);
        exchangeRateApiSteps = new ExchangeRateApiSteps();
    }

    @Test(priority = 1)
    @Description("Zephyr Step 1: Cookies-ზე დათანხმება ")
    public void closeCookie() {
        homePageSteps.acceptCookies();
    }

    @Test(
            priority = 2,
            dependsOnMethods = "closeCookie"
    )
    @Description("Zephyr Step 2: მენიუს გახსნა ")
    public void openMenu() {
        homePageSteps.openSideMenu();
    }

    @Test(
            priority = 3,
            dependsOnMethods = "openMenu"
    )
    @Description("Zephyr Step 3: ვალუტის კურსის გვერდის გახსნა")
    public void openCurrencyExchangePageAndValidatePage() {
        homePageSteps.clickCurrencyButton();
        currencyPageSteps
                .validateCurrencyPageUrl()
                .validateInputsAreVisible();
    }

    @Test(
            priority = 4,
            dependsOnMethods = "openCurrencyExchangePageAndValidatePage"
    )
    @Description("Zephyr Step 4: კონვერტორის გაყიდვის ველში EUR-ს არჩევა")
    public void selectSellCurrencyEUR() {
        currencyPageSteps.selectSellCurrency("EUR");
    }

    @Test(
            priority = 5,
            dependsOnMethods = "selectSellCurrencyEUR"
    )
    @Description("Zephyr Step 5: კონვერტორის ყიდვის ველში USD-ის არჩევა")
    public void selectBuyCurrencyUSD() {
        currencyPageSteps
                .selectBuyCurrency("USD")
                .validateSelectedCurrencies("EUR", "USD");
    }

    @Test(
            priority = 6,
            dependsOnMethods = "selectBuyCurrencyUSD"
    )
    @Description("Zephyr Step 6: თანხის შეყვანა")
    public void enterSellAmount() {
        currencyPageSteps
                .enterAmount("150")
                .validateConvertedAmountIsDisplayed();
    }

    @Test(
            priority = 7,
            dependsOnMethods = "enterSellAmount"
    )
    @Description("Zephyr Step 7: API-დან მიღებული გაცვლითი კურსის შედარება UI-ზე ნაჩვენებ კურსთან")
    public void validateEurToUsdExchangeRate() {

        ExchangeRateResponse exchangeRate =
                exchangeRateApiSteps.getExchangeRate("EUR", "USD");

        currencyPageSteps.validateConversionRate(
                "EUR",
                "USD",
                exchangeRate.getBuyRate()
        );
    }

    @Test(
            priority = 8,
            dependsOnMethods = "validateEurToUsdExchangeRate"
    )
    @Description("Zephyr Step 8: ვალუტების ადგილების შეცვლა")
    public void swapCurrencies() {
        currencyPageSteps
                .clickSwapCurrencyButton()
                .validateSelectedCurrencies("USD", "EUR");
    }

    @Test(
            priority = 9,
            dependsOnMethods = "swapCurrencies"
    )
    @Description("Zephyr Step 9: ახალი თანხის შეყვანა")
    public void enterNewAmountAfterSwap() {
        currencyPageSteps
                .enterAmount("120")
                .validateConvertedAmountIsDisplayed();
    }

    @Test(
            priority = 10,
            dependsOnMethods = "enterNewAmountAfterSwap"
    )
    @Description("Zephyr Step 10: API-დან მიღებული USD/EUR გაცვლითი კურსის შედარება UI-ზე ნაჩვენებ კურსთან")
    public void validateUsdToEurExchangeRate() {

        ExchangeRateResponse exchangeRate =
                exchangeRateApiSteps.getExchangeRate("USD", "EUR");

        currencyPageSteps.validateConversionRate(
                "USD",
                "EUR",
                exchangeRate.getBuyRate()
        );
    }
}
