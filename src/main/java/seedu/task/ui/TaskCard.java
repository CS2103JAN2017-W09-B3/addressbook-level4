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

    private static final String START_TIME_MESSAGE = "From: ";
    private static final String EVENT_END_MESSAGE = "To: ";
    private static final String DEADLINE_MESSAGE = "By: ";
    private static final String TAG_MESSAGE = "#";


    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        writeStartTime(task);
        writeEndTime(task);
        setCheckboxStatus(task.getCompletionStatus().getStatus());
        initTags(task);
    }

    private void writeEndTime(ReadOnlyTask task) {
        if(hasEndTime(task)){
            endTime.setText(getEndTimeMessage(task)+task.getEndTime().value);
        }else{
            endTime.setText("");
        }
    }

    private void writeStartTime(ReadOnlyTask task) {
        if(hasStartTime(task)){
            startTime.setText(START_TIME_MESSAGE+task.getStartTime().value);
        }else{
            startTime.setText("");
        }
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(TAG_MESSAGE+tag.tagName)));
    }

    private void setCheckboxStatus(boolean completionStatus){
        if(completionStatus){
            showChecked();
        }else{
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

    private String getEndTimeMessage(ReadOnlyTask task){
        String message = "";
        if(hasStartTime(task)){
            message = EVENT_END_MESSAGE;
        }else{
            message = DEADLINE_MESSAGE;
        }
        return message;
    }

    public boolean hasStartTime(ReadOnlyTask task){
        if(task.getStartTime().toString().equals("")){
            return false;
        }else{
            return true;
        }
    }

    public boolean hasEndTime(ReadOnlyTask task){
        if(task.getEndTime().toString().equals("")){
            return false;
        }else{
            return true;
        }
    }
}
