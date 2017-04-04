package seedu.task.model.task;

import seedu.task.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the taskmanager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    StartTime getStartTime();
    EndTime getEndTime();
    CompletionStatus getCompletionStatus();
    TaskType getTaskType();

    public static final String START_TIME_MESSAGE = "From: ";
    public static final String EVENT_END_MESSAGE = "To: ";
    public static final String DEADLINE_MESSAGE = "By: ";
    public static final String TAGS_MESSAGE = "Tags: ";
    public static final String TAG_PREFIX = "#";

    public static final String COMPLETION_STATUS_MESSAGE = "Completion Status: ";
    public static final String TASK_TYPE_MESSAGE = "Task Type: ";

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
//                && other.getStartTime().equals(this.getStartTime())
//                && other.getEndTime().equals(this.getEndTime())
                && other.getTaskType().equals(this.getTaskType())
                && other.getCompletionStatus().equals(this.getCompletionStatus()));
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName()).append("\n")
        .append(writeStartTime())
        .append(writeEndTime())
        .append(writeCompletionStatus())
        .append(writeTaskType())
        .append(writeTags());
        return builder.toString();
    }

    public default String writeTags() {
        StringBuilder tags = new StringBuilder();
        if (!getTags().asObservableList().isEmpty()) {
            tags.append(TAGS_MESSAGE);
            getTags().forEach(tags::append);
        }
        return tags.toString();
    }

    public default String writeCompletionStatus() {
        String completionStatus = "";
        completionStatus = COMPLETION_STATUS_MESSAGE + getCompletionStatus().toString() + "\n";
        return completionStatus;
    }

    public default String writeTaskType() {
        String taskType = "";
        taskType = TASK_TYPE_MESSAGE + getTaskType().getType() + "\n";
        return taskType;
    }

    public default String getEndTimeMessage() {
        String message = "";
        if (hasStartTime()) {
            message = EVENT_END_MESSAGE;
        } else {
            message = DEADLINE_MESSAGE;
        }
        return message;
    }

    public default boolean hasStartTime() {
        if (this.getStartTime().getValue() == null) {
            return false;
        } else {
            return true;
        }
    }

    public default boolean hasEndTime() {
        if (this.getEndTime().getValue() == null) {
            return false;
        } else {
            return true;
        }
    }

    public default String writeEndTime() {
        String result = "";
        if (hasEndTime()) {
            result = getEndTimeMessage() + getEndTime().toString() + "\n";
        }
        return result;
    }

    public default String writeStartTime() {
        String result = "";
        if (hasStartTime()) {
            result = START_TIME_MESSAGE + getStartTime().toString()  + "\n";
        }
        return result;
    }

}
