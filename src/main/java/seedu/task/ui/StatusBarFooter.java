package seedu.task.ui;

import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.model.SaveToRequestEvent;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.util.FxViewUtil;
import seedu.task.model.task.ReadOnlyTask;

//@@author A0139938L

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    @FXML
    private StatusBar progressStatus;
    @FXML
    private StatusBar saveLocationStatus;

    private static final String FXML = "StatusBarFooter.fxml";

    public StatusBarFooter(AnchorPane placeHolder, String saveLocation, ObservableList<ReadOnlyTask> observableList) {
        super(FXML);
        addToPlaceholder(placeHolder);
        updateProgressStatus(observableList);
        setSaveLocation("Saving to: ./" + saveLocation);
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

     public int getTotalTasks(ObservableList<ReadOnlyTask> observableList){
        return observableList.size();
    }

    public int getTotalCompletedTasks(ObservableList<ReadOnlyTask> observableList){
        int result = 0;
        ObservableList<ReadOnlyTask> tasks = observableList;
        for(ReadOnlyTask t : tasks){
            if(t.getCompletionStatus().getCompletion()){
                result++;
            }
        }
        return result;
    }

    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce) {
        updateProgressStatus(abce.data.getTaskList());
    }

    @Subscribe
    public void handleSaveToRequestEvent(SaveToRequestEvent event){
        setSaveLocation(event.filepath);
    }

}
//@@author
