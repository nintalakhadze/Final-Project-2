package ge.tbc.testautomation;

import ge.tbc.testautomation.steps.ChatSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.utils.Constants.HELLO_MESSAGE;
import static ge.tbc.testautomation.utils.Constants.REQUEST_END_CONVERSATION_MESSAGE;

@Epic("TBC Digital")
@Feature("Chat")
@Story("Chatbot Conversation")
public class ChatTest extends BaseTest {

    private ChatSteps chatSteps;

    private int botMessagesBeforeHello;
    private int botMessagesBeforeEndRequest;

    @BeforeClass(alwaysRun = true)
    public void initializeSteps() {
        chatSteps = new ChatSteps(page);
    }

    @Test(
            priority = 1,
            description = "KAN-T14 | Open chat"
    )
    @Description(
            "Open the chat and validate that the chat interface is displayed"
    )
    public void openChat() {

        homePageSteps
                .openSideMenu()
                .clickChatBtn();

        chatSteps
                .validateChatIsOpened();
    }

    @Test(
            priority = 2,
            dependsOnMethods = "openChat",
            description = "KAN-T14 | Send hello message and receive bot response"
    )
    @Description(
            "Send a hello message and validate that the sent message is displayed and a new bot response is received"
    )
    public void sendHelloMessage() {

        botMessagesBeforeHello =
                chatSteps.getBotMessagesCount();

        chatSteps
                .sendMessage(HELLO_MESSAGE)
                .validateSentMessage(HELLO_MESSAGE)
                .validateNewBotMessageReceived(
                        botMessagesBeforeHello
                )
                .validateLastBotMessageContains(
                        HELLO_MESSAGE
                );
    }

    @Test(
            priority = 3,
            dependsOnMethods = "sendHelloMessage",
            description = "KAN-T14 | Request conversation end"
    )
    @Description(
            "Request to end the conversation and validate that the sent message is displayed and a new bot response is received"
    )
    public void requestConversationEnd() {

        botMessagesBeforeEndRequest =
                chatSteps.getBotMessagesCount();

        chatSteps
                .sendMessage(REQUEST_END_CONVERSATION_MESSAGE)
                .validateSentMessage(REQUEST_END_CONVERSATION_MESSAGE)
                .validateNewBotMessageReceived(
                        botMessagesBeforeEndRequest
                )
                .finishConversationIfNeeded();
    }
}