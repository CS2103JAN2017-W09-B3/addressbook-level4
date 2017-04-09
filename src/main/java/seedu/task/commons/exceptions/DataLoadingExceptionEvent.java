package seedu.task.commons.exceptions;

import seedu.task.commons.events.BaseEvent;
//@@author A0139938L
/**
 * Indicates an exception during a file loading
 */
public class DataLoadingExceptionEvent extends BaseEvent {

    public Exception exception;
    private String message;

    public DataLoadingExceptionEvent(Exception exception) {
        this.exception = exception;
    }

    public DataLoadingExceptionEvent(String string) {
        this.message = string;
    }

    @Override
    public String toString() {
        if (message != null) {
            return message;
        } else {
            return exception.getMessage();
        }
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
//@@author
