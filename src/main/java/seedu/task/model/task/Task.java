package seedu.task.model.task;

import java.util.Date;
import java.util.Objects;

import seedu.task.commons.util.CollectionUtil;
import seedu.task.commons.util.NattyDateUtil;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.CompletionStatus.IncompleteType;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private StartTime startTime;
    private EndTime endTime;
    private CompletionStatus completionStatus;
    private TaskType taskType;

    private UniqueTagList tags;

    private static final String UPCOMING_PERIOD = "3 days later";

    /**
     * Only Name must be present and not null.
     */
    public Task(Name name, StartTime startTime, EndTime endTime,
            CompletionStatus completionStatus, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.completionStatus = completionStatus;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.updateTaskType();
        this.updateIncompleteType(this.getTaskType());
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartTime(), source.getEndTime(),
                source.getCompletionStatus(), source.getTags());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setStartTime(StartTime startTime) {
        assert startTime != null;
        this.startTime = startTime;
    }

    @Override
    public StartTime getStartTime() {
        return startTime;
    }

    public void setEndTime(EndTime endTime) {
        assert endTime != null;
        this.endTime = endTime;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
    }

    public void setCompletionStatus(CompletionStatus completionStatus) {
        assert completionStatus != null;
        this.completionStatus = completionStatus;
    }

    @Override
    public CompletionStatus getCompletionStatus() {
        return completionStatus;
    }

    /**
     * @return the taskType
     */
    @Override
    public TaskType getTaskType() {
        return taskType;
    }

    /**
     * @param taskType the taskType to set
     */
    public void setTaskType(TaskType taskType) {
        assert taskType != null;
        this.taskType = taskType;
    }

    public void updateTaskType() {
        //the task does not have end time, it is a SOMEDAY Task
        if (!this.hasEndTime()) {
            setTaskType(TaskType.SOMEDAY);
        //the task only has endtime, it is a DEADLINE Task
        } else if (!this.hasStartTime() && this.hasEndTime()) {
            setTaskType(TaskType.DEADLINE);
        //the task has start and end time, it is an EVENT Task
        } else if (this.hasStartTime() && this.hasEndTime()) {
            setTaskType(TaskType.EVENT);
        }
    }

    public void updateIncompleteType(TaskType taskType) {

        Date today = new Date();
        Date upcoming = NattyDateUtil.parseSingleDate(UPCOMING_PERIOD);

        switch(taskType) {
        //if task is EVENT task
        case EVENT:
            if (today.after(this.getEndTime().getValue())) {
                this.getCompletionStatus().setIncompleteType(IncompleteType.OVERDUE);
            } else if (upcoming.after(this.getStartTime().getValue())) {
                this.getCompletionStatus().setIncompleteType(IncompleteType.UPCOMING);
            } else {
                this.getCompletionStatus().setIncompleteType(IncompleteType.NORMAL);
            }
            break;
        case DEADLINE:
            if (today.after(this.getEndTime().getValue())) {
                this.getCompletionStatus().setIncompleteType(IncompleteType.OVERDUE);
            } else if (upcoming.after(this.getEndTime().getValue())) {
                this.getCompletionStatus().setIncompleteType(IncompleteType.UPCOMING);
            } else {
                this.getCompletionStatus().setIncompleteType(IncompleteType.NORMAL);
            }
            break;
        case SOMEDAY:
            this.getCompletionStatus().setIncompleteType(IncompleteType.NORMAL);
            break;
        //should never waterfall down to default case
        default:
            ;
        }
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setStartTime(replacement.getStartTime());
        this.setEndTime(replacement.getEndTime());
        this.setCompletionStatus(replacement.getCompletionStatus());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startTime, endTime, completionStatus, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
