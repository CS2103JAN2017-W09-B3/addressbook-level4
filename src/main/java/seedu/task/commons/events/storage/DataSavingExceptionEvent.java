package seedu.task.commons.events.storage;

import seedu.task.commons.events.BaseEvent;

//@@author A0139938L
/**
 * Indicates an exception during a file saving
 */
public class DataSavingExceptionEvent extends BaseEvent {

    public Exception exception;
    private String message;

    public DataSavingExceptionEvent(Exception exception) {
        this.exception = exception;
    }

    public DataSavingExceptionEvent(String string) {
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
