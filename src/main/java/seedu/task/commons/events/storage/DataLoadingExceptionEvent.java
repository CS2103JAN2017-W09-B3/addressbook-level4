package seedu.task.commons.events.storage;

import seedu.task.commons.events.BaseEvent;

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
        if(message != null){
            return message;
        }else{
        return exception.getMessage();
        }
    }
}
