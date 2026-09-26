package ge.tbc.testautomation.components;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ChatComponent {

    public final Locator chatIframe,
            messageInput,
            botGreetingMessage,
            botMessages;

    public final FrameLocator chatFrame;

    public ChatComponent(Page page) {

        chatIframe = page.locator(
                "iframe[title='Messaging window']"
        );

        chatFrame = page.frameLocator(
                "iframe[title='Messaging window']"
        );

        messageInput = chatFrame.locator(
                "#composer-input"
        );

        botGreetingMessage = chatFrame.getByText(
                "გამარჯობა, მე ვარ TBC AI - შენი ასისტენტი!",
                new FrameLocator.GetByTextOptions()
                        .setExact(false)
        );

        botMessages = chatFrame.locator(
                "div:has(> span:text-is('TBC · AI says:')) > span[tabindex='-1']"
        );
    }

    public Locator userMessage(String message) {
        return chatFrame
                .locator("div:has(> span:text-is('You say:'))")
                .getByText(
                        message,
                        new Locator.GetByTextOptions()
                                .setExact(true)
                );
    }
}