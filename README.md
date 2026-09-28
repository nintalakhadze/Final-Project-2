# TBC Digital – Advanced Test Automation

## Project Overview

This project contains automated tests for the TBC Digital website.

The project is built using Java, Playwright, TestNG, Rest Assured, MyBatis and H2 Database.

The framework covers UI testing, API testing, localization testing, database-driven testing, API-to-UI validation and network validation.

Application under test: https://www.tbcbank.ge


## Framework Architecture

The project is divided into several layers:

- Pages – contain page-specific elements and locators.
- Components – contain reusable UI elements and their actions.
- Steps – contain test actions, workflows and validations.
- API – contains API clients, API steps and response models.
- Database – contains database configuration, MyBatis mappers and models.
- Tests – contain automated test scenarios.

Reusable components are used to avoid duplicated code.

For example, HomePage uses HeaderComponent, SideMenuComponent and CookieComponent. Chat functionality is also separated into a reusable ChatComponent.

Components own their selectors and component-specific behavior, while page objects reuse these components. Steps contain test workflows and validations.

This structure makes the framework easier to maintain and reuse.


## Localization Strategy

The Loan Calculator scenario is tested in Georgian and English.

The same test logic is used for both languages. TestNG DataProvider provides the locale and expected localized texts.

The test validates localized content such as the loan navigation, calculator title, loan amount and loan term fields.

This approach avoids creating separate test logic for each language.


## SQL and Test Data Strategy

The CDM scenario uses test data stored in a local H2 database.

The test data flow is:

H2 Database → MyBatis → CdmMapper → CdmData → TestNG DataProvider → Playwright Test

MyBatis reads CDM records from the database and maps them to Java objects.

TestNG DataProvider provides these records to the automated test.

The same test logic runs with multiple database records. Adding a new database record does not require creating a new test method.

Database sessions are closed using try-with-resources.


## API Testing

Rest Assured is used for API testing.

The project contains a happy path and a negative scenario for the Offers API.

The happy path requests Cashback offers and validates the successful response.

The negative scenario sends an invalid Offer Type and validates the returned empty result.

API responses are deserialized into Java POJOs.

The tests validate response status codes and meaningful response data, including offers, paging information and nested partner data.


## API-to-UI Strategy

The Currency Exchange scenario validates that data displayed on the UI matches data received from the Exchange Rate API.

The API response is deserialized into ExchangeRateResponse.

The test uses the following values from the API:

- iso1
- iso2
- buyRate

Playwright validates that the selected currencies and exchange rate displayed on the UI match the API response.

The validation is performed for EUR/USD and again for USD/EUR after the currencies are swapped.

The API is used as the source of truth for this validation.


## Network Validation

Network validation is implemented in the Offers scenario.

When the Discount filter is selected on the UI, Playwright waits for and captures the related network response triggered by the user action.

The test validates:

- Endpoint
- HTTP method
- Response status
- Discount filter
- Locale
- Segment
- Page index
- Page size

After the network validation, the test also checks that the Discount filter is selected and offer cards are displayed on the UI.

This validates both the network communication and the resulting UI state.


## Test Stability

The framework uses Playwright synchronization and web-first assertions instead of fixed waits such as Thread.sleep().

Several possible sources of instability are handled by the framework:

1. Asynchronous network requests – Playwright waits for the required network response triggered by the UI action.

2. Dynamic UI content – Playwright assertions wait until the expected elements reach the required state.

3. Lazy-loaded CDM data – the test scrolls through the CDM list until the required record is found, using a limited number of attempts instead of a fixed Thread.sleep().

4. Page loading and navigation – validations are based on the expected page URL, visible elements and page state instead of arbitrary waiting time.

These approaches make the tests more stable without using arbitrary waits or unnecessary retries.


## Parallel Execution

TestNG is configured to run test classes in parallel.

The configuration is stored in testng.xml:

- Parallel mode: classes
- Thread count: 2

This allows independent test classes to execute at the same time.


## Zephyr Scale

Test scenarios are documented in Zephyr Scale.

The automated scenarios include:

- KAN-T10 – Currency Exchange and API-to-UI consistency
- KAN-T12 – Offers Network Validation
- KAN-T13 – Loan Calculator Localization
- KAN-T14 – Chat
- KAN-T15 – CDM Database-Driven Validation
- KAN-T16 – Cashback Offers API Happy Path
- KAN-T17 – Invalid Offer Type API Negative Scenario

Automated tests contain their related Zephyr scenario IDs for traceability.


## Running Tests

The complete test suite can be executed with Maven:

mvn clean test

Test execution is configured through testng.xml.

Latest successful execution:

Tests run: 54  
Failures: 0  
Errors: 0  
Skipped: 0  
Result: BUILD SUCCESS


## Reporting

Allure is used for test reporting.

The tests use Allure annotations such as Epic, Feature, Story and Description to organize test results and provide readable test information.

Zephyr scenario IDs are included in the automated tests to provide traceability between Zephyr scenarios and automation.