//@@author A0139410N
package seedu.task.logic.commands;

import seedu.task.model.task.TaskType;

/**
 * Lists all deadline tasks in the task manager to the user.
 */
public class ListDeadlineCommand extends Command {

    public static final String LIST_COMMAND_WORD = TaskType.DEADLINE.toString();

    public static final String MESSAGE_SUCCESS = "Listed all " + LIST_COMMAND_WORD + " tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowDeadline();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
