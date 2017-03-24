package seedu.task.ui.chat;

import seedu.task.model.chat.Chat;

public class SuruChatMessage extends ChatMessage {
    protected static final String FXML = "SuruChatMessage.fxml";

    public SuruChatMessage(Chat chat) {
        super(FXML, chat);
    }

}
