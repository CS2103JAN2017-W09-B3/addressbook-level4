package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's completion status in the task manager.
 */
public class CompletionStatus {

    public static final String MESSAGE_COMPLETION_STATUS_CONSTRAINTS = "currentStatus should only be "
            + "normal, upcoming or overdue";
    /*
     * Tasks can be completed or not completed, represented by a boolean isComplete
     */
    private boolean isComplete;
    /*
     * If task is not completed, it can be further broken down to normal, upcoming and overdue
     */
    private enum incompleteType {

        NORMAL("normal"), UPCOMING("upcoming"), OVERDUE("overdue");
        private String type;

        private incompleteType(String type) {
            this.type = type;
        }

    }

    private incompleteType currentStatus;

    /**
     * Sets isComplete of CompletionStatus to argument
     */
    public CompletionStatus(boolean completionStatus) {
        this.isComplete = completionStatus;
        this.currentStatus = incompleteType.NORMAL;
    }

    public void swapCompletion() {
        this.isComplete = !this.isComplete;
    }

    public boolean getCompletion() {
        return isComplete;
    }

    public void setIncompleteType(String type) throws IllegalValueException {
        assert type != null;
        switch (type) {
        case "normal":
            this.currentStatus = incompleteType.NORMAL;
            break;
        case "upcoming":
            this.currentStatus = incompleteType.UPCOMING;
            break;
        case "overdue":
            this.currentStatus = incompleteType.OVERDUE;
            break;
        default:
            throw new IllegalValueException(MESSAGE_COMPLETION_STATUS_CONSTRAINTS);
        }
    }


    @Override
    public String toString() {
        if (this.isComplete) {
            return String.valueOf(isComplete);
        } else {
            return this.currentStatus.type;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompletionStatus // instanceof handles nulls
                        && this.isComplete == ((CompletionStatus) other).isComplete
                        && this.currentStatus == ((CompletionStatus) other).currentStatus); // state check
    }


}
