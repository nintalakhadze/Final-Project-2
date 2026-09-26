package ge.tbc.testautomation;

import ge.tbc.testautomation.steps.ChatSteps;
import ge.tbc.testautomation.steps.HomePageSteps;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ChatTest extends BaseTest {

    private HomePageSteps homePageSteps;
    private ChatSteps chatSteps;

    private int botMessagesBeforeHello;
    private int botMessagesBeforeEndRequest;

    @BeforeClass
    public void initializeSteps() {
        homePageSteps = new HomePageSteps(page);
        chatSteps = new ChatSteps(page);
    }

    @Test(priority = 1)
    @Description("Zephyr Step 1: Cookie შეტყობინების დახურვა")
    public void closeCookie() {
        homePageSteps.acceptCookies();
    }

    @Test(
            priority = 2,
            dependsOnMethods = "closeCookie"
    )
    @Description("Zephyr Step 2: სწრაფი მოქმედებების მენიუს გახსნა")
    public void openMenu() {
        homePageSteps.openSideMenu();
    }

    @Test(
            priority = 3,
            dependsOnMethods = "openMenu"
    )
    @Description("Zephyr Step 3: ჩატის გახსნა")
    public void openChat() {
        homePageSteps
                .clickChatBtn();

        chatSteps
                .validateChatIsOpened();
    }

    @Test(
            priority = 4,
            dependsOnMethods = "openChat"
    )
    @Description("Zephyr Step 4: შეტყობინების გაგზავნა და ბოტის პასუხის მიღება")
    public void sendHelloMessage() {

        botMessagesBeforeHello =
                chatSteps.getBotMessagesCount();

        chatSteps
                .sendMessage("გამარჯობა")
                .validateSentMessage("გამარჯობა")
                .validateNewBotMessageReceived(
                        botMessagesBeforeHello
                )
                .validateLastBotMessageContains(
                        "გამარჯობა"
                );
    }

    @Test(
            priority = 5,
            dependsOnMethods = "sendHelloMessage"
    )
    @Description("Zephyr Step 5: საუბრის დასრულების მოთხოვნა")
    public void requestConversationEnd() {

        botMessagesBeforeEndRequest =
                chatSteps.getBotMessagesCount();

        chatSteps
                .sendMessage("საუბრის დასრულება")
                .validateSentMessage("საუბრის დასრულება")
                .validateNewBotMessageReceived(
                        botMessagesBeforeEndRequest
                )
                .finishConversationIfNeeded();
    }

    @Test(
            priority = 6,
            dependsOnMethods = "requestConversationEnd"
    )
    @Description("Zephyr Step 6: შეფასების ფორმის გამოჩენა")
    public void validateSurveyDisplayed() {

        chatSteps.validateSurveyIsDisplayed();
    }
}