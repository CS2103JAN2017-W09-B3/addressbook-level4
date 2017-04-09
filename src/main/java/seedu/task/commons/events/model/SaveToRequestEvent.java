package seedu.task.commons.events.model;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.ReadOnlyTaskManager;
//@@author A0139938L
/** Indicates the file path to save to has changed*/
public class SaveToRequestEvent extends BaseEvent {

    public final String filepath;
    public ReadOnlyTaskManager taskManager;

    public SaveToRequestEvent(ReadOnlyTaskManager taskManager, String filepath) {
        this.taskManager = taskManager;
        this.filepath = filepath;
    }

    @Override
    public String toString() {
        return filepath;
    }
}
