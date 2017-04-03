//@@author A0139410N
package seedu.task.model.task;

/**
 * Represents a task's type in the sense of someday, event or deadline task
 */
public enum TaskType {

    SOMEDAY("someday"), EVENT("event"), DEADLINE("deadline");
    private String type;

    private TaskType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return this.getType();
    }
}
