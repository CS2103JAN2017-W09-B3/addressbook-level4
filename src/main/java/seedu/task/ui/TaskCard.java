package seedu.task.ui;
//@@author A0139938L
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.task.commons.util.FxViewUtil;
import seedu.task.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private AnchorPane cardAnchorPane;
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
    @FXML
    private Label taskTypeLabel;


    private final String BACKGROUND_COLOR_STYLE = "-fx-background-color: ";
    private final String TEXT_FILL_STYLE = "-fx-text-fill: ";
    private final String FONT_SIZE_STYLE = "-fx-font-size: ";
    private final String OVERDUE_COLOR = "#d55454";
    private final String UPCOMING_COLOR = "orange";

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName + " ");
        id.setText(displayedIndex + ". ");
        startTime.setText(task.writeStartTime());
        endTime.setText(task.writeEndTime());
        String completionStatus = task.getCompletionStatus().toString().toUpperCase();
        taskTypeLabel.setText(completionStatus);
        setTaskTypeTextColor(completionStatus);
        setCheckboxStatus(task.getCompletionStatus().getCompletion());
        initTags(task);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);

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

    /**
     * Sets the color of the task type label.
     * @param type
     */
    private void setTaskTypeTextColor(String type) {
        switch(type) {
        case "OVERDUE":
            setLabelStyle(OVERDUE_COLOR);
            break;
        case "UPCOMING":
            setLabelStyle(UPCOMING_COLOR);
            break;
        default:
            taskTypeLabel.setStyle("-fx-text-fill: none;");
            break;
        }
    }

    /**
     * Sets the background color of the label
     * Sets the text color to white
     * Sets the font size to 8pt
     * @param backgroundColor
     */
    private void setLabelStyle(String backgroundColor) {
        String textFill = "white";
        String fontSize = "8pt";

        String style =
                BACKGROUND_COLOR_STYLE + backgroundColor + ";" +
                TEXT_FILL_STYLE + textFill + ";" +
                FONT_SIZE_STYLE + fontSize + ";";

        taskTypeLabel.setStyle(style);
    }

}
//@@author
