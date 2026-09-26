package ge.tbc.testautomation.apiSteps;

import ge.tbc.testautomation.api.ExchangeRateApi;
import ge.tbc.testautomation.models.ExchangeRateResponse;

public class ExchangeRateApiSteps {

    private final ExchangeRateApi exchangeRateApi;

    public ExchangeRateApiSteps() {
        exchangeRateApi = new ExchangeRateApi();
    }

    public ExchangeRateResponse getExchangeRate(String sellCurrency,
                                                String buyCurrency) {
        return exchangeRateApi.getExchangeRate(
                sellCurrency,
                buyCurrency
        );
    }
}
