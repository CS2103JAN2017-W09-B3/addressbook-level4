package seedu.task.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.task.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView imgChecked;
    @FXML
    private ImageView imgUnchecked;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        startTime.setText(task.writeStartTime());
        endTime.setText(task.writeEndTime());
        setCheckboxStatus(task.getCompletionStatus().getStatus());
        initTags(task);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(ReadOnlyTask.TAG_PREFIX + tag.tagName)));
    }

    private void setCheckboxStatus(boolean completionStatus) {
        if (completionStatus) {
            showChecked();
        } else {
            showUnchecked();
        }
    }

    private void showUnchecked() {
        imgChecked.setVisible(false);
        imgUnchecked.setVisible(true);
    }

    private void showChecked() {
        imgChecked.setVisible(true);
        imgUnchecked.setVisible(false);
    }


}
