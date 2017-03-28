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

    public static final String START_DATE_MESSAGE = "Start Date: ";
    public static final String END_DATE_MESSAGE = "End Date: ";
    public static final String COMPLETION_STATUS_MESSAGE = "Completion Status: ";
    public static final String TAGS_MESSAGE = "Tags: ";

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
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndTime())
                && other.getCompletionStatus().equals(this.getCompletionStatus()));
    }

    /**
     * Formats the task as text, showing all task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName()).append("\n")
        .append(writeStartDate())
        .append(writeEndDate())
        .append(writeCompletionStatus())
        .append(writeTags());
        return builder.toString();
    }

    public default String writeStartDate() {
        String startDate = "";
        if (!getStartTime().toString().equals("")) {
            startDate = START_DATE_MESSAGE + getStartTime().toString() + ("\n");
        }
        return startDate;
    }

    public default String writeEndDate() {
        String endDate = "";
        if (!getEndTime().toString().equals("")) {
            endDate = END_DATE_MESSAGE + getEndTime().toString() + ("\n");
        }
        return endDate;
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
        completionStatus = COMPLETION_STATUS_MESSAGE + getCompletionStatus().toString() + ("\n");
        return completionStatus;
    }

}
