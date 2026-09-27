package ge.tbc.testautomation.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class FeedbackSurveyComponent {

    public final Locator surveyIframe;
    public final Locator surveyOverlay;

    public FeedbackSurveyComponent(Page page) {
        surveyIframe = page.locator("iframe[title='Feedback Survey']");
        surveyOverlay = page.locator("#MDigitalLightboxWrapper");
    }
}