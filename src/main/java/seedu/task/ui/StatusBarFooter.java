package seedu.task.ui;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.task.commons.events.model.SaveToRequestEvent;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.util.FxViewUtil;
import seedu.task.model.task.ReadOnlyTask;

//@@author A0139938L

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {
    //private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    @FXML
    private StatusBar progressStatus;
    @FXML
    private StatusBar saveLocationStatus;

    private static final String FXML = "StatusBarFooter.fxml";
    private static final String SAVING_MESSAGE = "Saving to: ";

    public StatusBarFooter(AnchorPane placeHolder, String saveLocation, ObservableList<ReadOnlyTask> observableList) {
        super(FXML);
        addToPlaceholder(placeHolder);
        updateProgressStatus(observableList);
        updateBackgroundColor(observableList);
        setSaveLocation(SAVING_MESSAGE + saveLocation);
        registerAsAnEventHandler(this);
    }

    private void addToPlaceholder(AnchorPane placeHolder) {
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(getRoot());
    }

    private void setSaveLocation(String location) {
        this.saveLocationStatus.setText(location);
    }

    private void updateProgressStatus(ObservableList<ReadOnlyTask> observableList) {
        String progress = null;
        int totalTasks = getTotalTasks(observableList);
        int completedTasks = getTotalCompletedTasks(observableList);

        progress = completedTasks + "/" + totalTasks + " tasks completed.";
        this.progressStatus.setText(progress);
    }

    public int getTotalTasks(ObservableList<ReadOnlyTask> observableList) {
        return observableList.size();
    }

    private void updateBackgroundColor(ObservableList<ReadOnlyTask> observableList) {
        double totalTasks = getTotalTasks(observableList);
        double completedTasks = getTotalCompletedTasks(observableList);
        double progress = 0;
        if (totalTasks > 0) {
            progress = completedTasks / totalTasks;
        }
        if (progress > 0.5) {
            progressStatus.setStyle("-fx-background-color: #CFF48D;"); //green
            saveLocationStatus.setStyle("-fx-background-color: #CFF48D;");
        } else {
            progressStatus.setStyle("-fx-background-color: #f4a68c;"); //pink
            saveLocationStatus.setStyle("-fx-background-color: #f4a68c;");
        }
    }

    public int getTotalCompletedTasks(ObservableList<ReadOnlyTask> observableList) {
        int result = 0;
        ObservableList<ReadOnlyTask> tasks = observableList;
        for (ReadOnlyTask t : tasks) {
            if (t.getCompletionStatus().getCompletion()) {
                result++;
            }
        }
        return result;
    }

    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce) {
        updateProgressStatus(abce.data.getTaskList());
        updateBackgroundColor(abce.data.getTaskList());
    }

    @Subscribe
    public void handleSaveToRequestEvent(SaveToRequestEvent event) {
        setSaveLocation(SAVING_MESSAGE + event.filepath);
    }

}
//@@author
