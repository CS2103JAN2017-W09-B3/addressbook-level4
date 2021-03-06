package guitests.guihandles;
//@@author A0139938L
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String START_TIME_FIELD_ID = "#startTime";
    private static final String END_TIME_FIELD_ID = "#endTime";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String ARGUMENTS_PATTERN = "^.+:\\s(?<time>.+)\\R";

    private static final Pattern ARGUMENTS_FORMAT =
            Pattern.compile(ARGUMENTS_PATTERN, Pattern.CASE_INSENSITIVE);

    public String getTime(String label) {
        String time = getTextFromLabel(label);
        Matcher matcher = ARGUMENTS_FORMAT.matcher(time);
        if (!matcher.matches()) {
            return "";
        } else {
            time = matcher.group("time");
            return time;
        }
    }

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }


    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getStartTime() {
        return getTime(START_TIME_FIELD_ID);
    }

    public String getEndTime() {
        return getTime(END_TIME_FIELD_ID);
    }

    public List<String> getTags() {
        return getTags(getTagsContainer());
    }

    private List<String> getTags(Region tagsContainer) {
        return tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(node -> ((Labeled) node).getText())
                .collect(Collectors.toList());
    }

    private List<String> getTags(UniqueTagList tags) {
        return tags
                .asObservableList()
                .stream()
                .map(tag -> tag.toString().trim())
                .collect(Collectors.toList());
    }

    private Region getTagsContainer() {
        return guiRobot.from(node).lookup(TAGS_FIELD_ID).query();
    }

    public boolean isSameTask(ReadOnlyTask task) {
        String name = getName();
        String startTime = getStartTime();
        String endTime = getEndTime();
        List<String> tags = getTags();

        String expectedName = task.getName().toString();
        String expectedStartTime = task.getStartTime().toString();
        String expectedEndTime = task.getEndTime().toString();
        List<String> expectedTags = getTags(task.getTags());

        boolean isNameEqual = name.equals(expectedName);
        boolean isStartTimeEqual = startTime.equals(expectedStartTime);
        boolean isEndTimeEqual = endTime.equals(expectedEndTime);
        boolean areTagsEqual = tags.equals(expectedTags);

        return isNameEqual && isStartTimeEqual && isEndTimeEqual && areTagsEqual;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getName().equals(handle.getName())
                    && getStartTime().equals(handle.getStartTime())
                    && getEndTime().equals(handle.getEndTime())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName();
    }

}
