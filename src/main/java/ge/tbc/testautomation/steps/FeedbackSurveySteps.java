//package ge.tbc.testautomation.steps;
//
//import com.microsoft.playwright.Page;
//import ge.tbc.testautomation.components.FeedbackSurveyComponent;
//
//public class FeedbackSurveySteps {
//
//    private final FeedbackSurveyComponent feedbackSurveyComponent;
//
//    public FeedbackSurveySteps(Page page) {
//        feedbackSurveyComponent = new FeedbackSurveyComponent(page);
//    }
//
//    public FeedbackSurveySteps dismissSurveyIfVisible() {
//
//        if (feedbackSurveyComponent.surveyOverlay.isVisible()) {
//            feedbackSurveyComponent.surveyOverlay.evaluate(
//                    "element => element.remove()"
//            );
//        }
//
//        return this;
//    }
//}