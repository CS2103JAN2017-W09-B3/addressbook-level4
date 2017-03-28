package seedu.task.ui;

import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import seedu.task.model.task.ReadOnlyTask;

/**
 * Panel containing the list of tasks.
 */
public class UndoneTaskListPanel extends TaskListPanel {
    private static final String FXML = "UndoneTaskListPanel.fxml";

    public UndoneTaskListPanel(AnchorPane taskListPlaceholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML, taskListPlaceholder, taskList);
    }


}


