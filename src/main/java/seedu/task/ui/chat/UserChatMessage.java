package seedu.task.ui.chat;

import seedu.task.model.chat.Chat;

public class UserChatMessage extends ChatMessage {
    protected static final String FXML = "UserChatMessage.fxml";

    public UserChatMessage(Chat chat) {
        super(FXML, chat);
    }
}
