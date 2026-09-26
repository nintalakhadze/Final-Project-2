package ge.tbc.testautomation.utils;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "localizationData", parallel = true)
    public static Object[][] localizationData() {
        return new Object[][]{
                {
                        "ka",
                        "ჩემთვის",
                        "სესხები",
                        "სესხის მოთხოვნა",
                        "სესხის კალკულატორი",
                        "სესხის თანხა",
                        "სესხის ვადა"
                },
                {
                        "en",
                        "Personal",
                        "Loans",
                        "Get a Loan",
                        "Loan Calculator",
                        "Loan Amount",
                        "Loan Term"
                }
        };
    }
}