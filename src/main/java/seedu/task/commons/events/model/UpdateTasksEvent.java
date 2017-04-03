package seedu.task.commons.events.model;

import seedu.task.commons.events.BaseEvent;

/** Indicates a request for tasks to be refreshed*/
public class UpdateTasksEvent extends BaseEvent {

    public UpdateTasksEvent() {
    }

    @Override
    public String toString() {
        return "tasks refreshed";
    }
}
