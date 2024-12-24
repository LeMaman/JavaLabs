package chat.application;

import java.util.ArrayList;
import java.util.List;

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

// Основна реалізація
interface Message {
    String getContent();
}

class BasicMessage implements Message {
    private final String content;

    public BasicMessage(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }
}

abstract class MessageDecorator implements Message {
    protected Message message;

    public MessageDecorator(Message message) {
        this.message = message;
    }

    @Override
    public String getContent() {
        return message.getContent();
    }
}

class EncryptedMessageDecorator extends MessageDecorator {
    public EncryptedMessageDecorator(Message message) {
        super(message);
    }

    @Override
    public String getContent() {
        return encrypt(message.getContent());
    }

    private String encrypt(String content) {
        // чогось оригінальнішого не придумав
        return new StringBuilder(content).reverse().toString();
    }
}

class EmojiMessageDecorator extends MessageDecorator {
    public EmojiMessageDecorator(Message message) {
        super(message);
    }

    @Override
    public String getContent() {
        return message.getContent() + " 😊";
    }
}

interface ChatMediator {
    void addUser(User user);
    void sendMessage(String message, User sender);
    List<User> getUsers();
}

class ChatMediatorImpl implements ChatMediator {
    private final List<User> users = new ArrayList<>();

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void sendMessage(String message, User sender) {
        for (User user : users) {
            if (user != sender) {
                user.receiveMessage(message);
            }
        }
    }

    public List<User> getUsers() {
        return users;
    }
}

abstract class User {
    protected ChatMediator mediator;
    protected String name;
    protected List<String> receivedMessages = new ArrayList<>();

    public User(ChatMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public abstract void sendMessage(String message);

    public void receiveMessage(String message) {
        receivedMessages.add(message);
        System.out.println(name + " received: " + message);
    }

    public List<String> getReceivedMessages() {
        return receivedMessages;
    }
}

class UserImpl extends User {
    public UserImpl(ChatMediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(name + " sends: " + message);
        mediator.sendMessage(message, this);
    }
}
