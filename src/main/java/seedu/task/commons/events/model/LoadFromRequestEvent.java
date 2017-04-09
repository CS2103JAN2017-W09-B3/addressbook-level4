package seedu.task.commons.events.model;

import java.util.Optional;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.ReadOnlyTaskManager;
//@@author A0139938L
/** Indicates the file path to load from has changed*/
public class LoadFromRequestEvent extends BaseEvent {

    public final String filepath;
    public Optional<ReadOnlyTaskManager> taskManager;

    public LoadFromRequestEvent(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public String toString() {
        return filepath;
    }

    /**
     * @return the taskManager
     */
    public Optional<ReadOnlyTaskManager> getTaskManager() {
        return taskManager;
    }
}
