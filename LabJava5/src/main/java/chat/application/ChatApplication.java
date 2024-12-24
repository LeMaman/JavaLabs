package chat.application;

public class ChatApplication {
    public static void main(String[] args) {
        ChatMediator mediator = new ChatMediatorImpl();

        User user1 = new UserImpl(mediator, "Alice");
        User user2 = new UserImpl(mediator, "Bob");
        User user3 = new UserImpl(mediator, "Charlie");

        mediator.addUser(user1);
        mediator.addUser(user2);
        mediator.addUser(user3);

        Message message = new BasicMessage("Hello, everyone!");
        message = new EncryptedMessageDecorator(message);
        message = new EmojiMessageDecorator(message);

        user1.sendMessage(message.getContent());
    }
}