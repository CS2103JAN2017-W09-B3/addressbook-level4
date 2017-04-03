//@@author A0139410N
package seedu.task.model.task;

/**
 * Represents a Task's completion status in the task manager.
 */
public class CompletionStatus {

    public static final String MESSAGE_COMPLETION_STATUS_CONSTRAINTS = "currentStatus should only be "
            + "NORMAL, UPCOMING or OVERDUE";
    /*
     * Tasks can be completed or not completed, represented by a boolean isComplete
     */
    private boolean isComplete;
    /*
     * If task is not completed, it can be further broken down to normal, upcoming and overdue
     */
    public enum IncompleteType {

        NORMAL("normal"), UPCOMING("upcoming"), OVERDUE("overdue");
        private String type;

        private IncompleteType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

    }

    private IncompleteType currentStatus;

    /**
     * Sets isComplete of CompletionStatus to argument
     */
    public CompletionStatus(boolean completionStatus) {
        this.isComplete = completionStatus;
        this.currentStatus = IncompleteType.NORMAL;
    }

    public void swapCompletion() {
        this.isComplete = !this.isComplete;
    }

    public boolean getCompletion() {
        return isComplete;
    }

    public void setIncompleteType(IncompleteType type) {
        assert type != null;
        switch (type) {
        case NORMAL:
            this.currentStatus = IncompleteType.NORMAL;
            break;
        case UPCOMING:
            this.currentStatus = IncompleteType.UPCOMING;
            break;
        case OVERDUE:
            this.currentStatus = IncompleteType.OVERDUE;
            break;
        //this switch case should never waterfall down to default
        default:
            ;
        }
    }


    @Override
    public String toString() {
        if (this.isComplete) {
            return String.valueOf(isComplete);
        } else {
            return this.currentStatus.getType();
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
