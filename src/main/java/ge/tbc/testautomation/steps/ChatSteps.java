package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import ge.tbc.testautomation.components.ChatComponent;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static ge.tbc.testautomation.utils.Constants.*;

public class ChatSteps {

    private final Page page;
    private final ChatComponent chatComponent;

    public ChatSteps(Page page) {
        this.page = page;
        this.chatComponent = new ChatComponent(page);
    }

    public ChatSteps validateChatIsOpened() {

        assertThat(chatComponent.chatIframe)
                .isVisible(
                        new LocatorAssertions.IsVisibleOptions()
                                .setTimeout(DEFAULT_UI_TIMEOUT)
                );

        assertThat(chatComponent.messageInput)
                .isVisible(
                        new LocatorAssertions.IsVisibleOptions()
                                .setTimeout(DEFAULT_UI_TIMEOUT)
                );

        assertThat(chatComponent.botGreetingMessage)
                .isVisible(
                        new LocatorAssertions.IsVisibleOptions()
                                .setTimeout(DEFAULT_UI_TIMEOUT)
                );

        return this;
    }

    public ChatSteps sendMessage(String message) {

        chatComponent.messageInput.fill(message);
        chatComponent.messageInput.press(ENTER_KEY);

        return this;
    }

    public ChatSteps validateSentMessage(String message) {

        assertThat(chatComponent.userMessage(message))
                .isVisible();

        return this;
    }

    public int getBotMessagesCount() {
        return chatComponent.botMessages.count();
    }

    public ChatSteps validateNewBotMessageReceived(int previousCount) {

        assertThat(chatComponent.botMessages)
                .hasCount(
                        previousCount + 1,
                        new LocatorAssertions.HasCountOptions()
                                .setTimeout(DEFAULT_UI_TIMEOUT)
                );

        return this;
    }

    public ChatSteps validateLastBotMessageContains(
            String expectedText
    ) {

        assertThat(chatComponent.botMessages.last())
                .containsText(expectedText);

        return this;
    }

    public ChatSteps finishConversationIfNeeded() {

        String lastBotMessage =
                chatComponent.botMessages
                        .last()
                        .innerText();

        if (lastBotMessage.contains(CHAT_HELP_QUESTION)) {

            int botMessagesBeforeNo =
                    getBotMessagesCount();

            sendMessage(CHAT_NO_RESPONSE);
            validateSentMessage(CHAT_NO_RESPONSE);
            validateNewBotMessageReceived(botMessagesBeforeNo);
        }

        return this;
    }

    public ChatSteps validateSurveyIsDisplayed() {

        page.waitForCondition(
                chatComponent::isSurveyDisplayed,
                new Page.WaitForConditionOptions()
                        .setTimeout(SURVEY_TIMEOUT)
        );

        Frame medalliaFrame =
                chatComponent.getMedalliaFrame();

        if (medalliaFrame == null) {
            throw new AssertionError(
                    MEDALLIA_FRAME_NOT_FOUND_MESSAGE
            );
        }

        assertThat(
                chatComponent.surveyContainer(medalliaFrame)
        ).isVisible();

        return this;
    }
}