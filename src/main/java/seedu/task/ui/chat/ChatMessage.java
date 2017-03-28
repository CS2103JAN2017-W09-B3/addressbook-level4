package seedu.task.ui.chat;

//@@author A0139938L
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.task.model.chat.Chat;
import seedu.task.ui.UiPart;

public class ChatMessage extends UiPart<Region> {
    @FXML
    private StackPane chatMessagePane;
    @FXML
    protected Label message;

    public ChatMessage(String fxml, Chat chat) {
        super(fxml);
        message.setText(chat.getMessage());
    }

}
//@@author A0139938L
