package chat.application;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChatSystemTests {

    @Test
    public void testBasicMessage() {
        Message message = new BasicMessage("Test Message");
        assertEquals("Test Message", message.getContent());
    }

    @Test
    public void testEncryptedMessageDecorator() {
        Message message = new BasicMessage("Test Message");
        message = new EncryptedMessageDecorator(message);
        assertNotEquals("Test Message", message.getContent());
        // Енкріпшн повинен приховувати справжній текст
    }

    @Test
    public void testEmojiMessageDecorator() {
        Message message = new BasicMessage("Hello");
        message = new EmojiMessageDecorator(message);
        assertTrue(message.getContent().contains("😊"));
        // Сподіваємося, додалося емоджі
    }

    @Test
    public void testMediatorAddsUsers() {
        ChatMediator mediator = new ChatMediatorImpl();
        User user = new UserImpl(mediator, "TestUser");
        mediator.addUser(user);
        assertTrue(mediator.getUsers().contains(user));
    }

    @Test
    public void testUserSendsMessage() {
        ChatMediator mediator = new ChatMediatorImpl();
        User user1 = new UserImpl(mediator, "Alice");
        User user2 = new UserImpl(mediator, "Bob");
        mediator.addUser(user1);
        mediator.addUser(user2);

        Message message = new BasicMessage("Hello, Bob!");
        user1.sendMessage(message.getContent());

        // ще не певен щодо реалізації цього, але очікую, що зможу відтворити
        assertTrue(user2.getReceivedMessages().contains("Hello, Bob!"));
    }
}

