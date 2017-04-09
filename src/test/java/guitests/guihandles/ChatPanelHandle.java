package guitests.guihandles;

import java.util.Set;

import guitests.GuiRobot;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.task.TestApp;
import seedu.task.model.chat.Chat;
import seedu.task.testutil.TestUtil;
//@@author A0139938L
/**
 * Provides a handle for the panel containing the chat messages.
 */
public class ChatPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String SURU_CHAT_MESSAGE_ID = "#suruMessage";
    public static final String USER_CHAT_MESSAGE_ID = "#userMessage";


    private static final String CHAT_LIST_VIEW_ID = "#chat-list-view";

    public ChatPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public ListView<Chat> getListView() {
        return getNode(CHAT_LIST_VIEW_ID);
    }

    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point = TestUtil.getScreenMidPoint(getListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns the position of the chat message given, {@code NOT_FOUND} if not found in the list.
     */
    public int getChatIndex(Chat targetChat) {
        ObservableList<Chat> chatsInList = getListView().getItems();
        for (int i = 0; i < chatsInList.size(); i++) {
            if (chatsInList.get(i).getMessage().equals(targetChat.getMessage())) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a chat from the list by index
     */
    public Chat getChat(int index) {
        return getListView().getItems().get(index);
    }

    public int getListSize() {
        return getListView().getItems().size();
    }

    public Chat getLastChat() {
        ListView<Chat> chatList = getListView();
        ObservableList<Chat> chatObservableList = chatList.getItems();
        Chat chat = chatObservableList.get(getListSize() - 1);
        return chat;
    }

    public Set<Node> getAllSuruMessages() {
        return guiRobot.lookup(SURU_CHAT_MESSAGE_ID).queryAll();
    }

    public Set<Node> getAllUserMessages() {
        return guiRobot.lookup(USER_CHAT_MESSAGE_ID).queryAll();
    }
}
