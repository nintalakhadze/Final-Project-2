package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import ge.tbc.testautomation.components.ChatComponent;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

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
                                .setTimeout(15_000)
                );

        assertThat(chatComponent.messageInput)
                .isVisible(
                        new LocatorAssertions.IsVisibleOptions()
                                .setTimeout(15_000)
                );

        assertThat(chatComponent.botGreetingMessage)
                .isVisible(
                        new LocatorAssertions.IsVisibleOptions()
                                .setTimeout(15_000)
                );

        return this;
    }

    public ChatSteps sendMessage(String message) {
        chatComponent.messageInput.fill(message);
        chatComponent.messageInput.press("Enter");

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
                                .setTimeout(15_000)
                );

        return this;
    }

    public ChatSteps validateLastBotMessageContains(String expectedText) {
        assertThat(chatComponent.botMessages.last())
                .containsText(expectedText);

        return this;
    }

    public ChatSteps finishConversationIfNeeded() {

        String lastBotMessage = chatComponent.botMessages
                .last()
                .innerText();

        if (lastBotMessage.contains(
                "სხვა რამეში ხომ არ შემიძლია დაგეხმარო"
        )) {

            int botMessagesBeforeNo = getBotMessagesCount();

            sendMessage("არა");
            validateSentMessage("არა");
            validateNewBotMessageReceived(botMessagesBeforeNo);
        }

        return this;
    }

    public ChatSteps validateSurveyIsDisplayed() {

        page.waitForCondition(
                () -> page.frames().stream()
                        .anyMatch(frame ->
                                frame.name().equals("web_messenger_ref")
                                        && frame.locator(
                                        "#kampyleFormContainer:visible"
                                ).count() > 0
                        ),
                new Page.WaitForConditionOptions()
                        .setTimeout(30_000)
        );

        Frame medalliaFrame = page.frames().stream()
                .filter(frame ->
                        frame.name().equals("web_messenger_ref")
                )
                .findFirst()
                .orElseThrow(() ->
                        new AssertionError(
                                "Medallia frame not found"
                        )
                );

        Locator surveyContainer = medalliaFrame.locator(
                "#kampyleFormContainer:visible"
        );

        assertThat(surveyContainer).isVisible();

        return this;
    }
}