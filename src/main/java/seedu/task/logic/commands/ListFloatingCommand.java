//@@author A0139410N
package seedu.task.logic.commands;

import seedu.task.model.task.TaskType;

/**
 * Lists all floating tasks in the task manager to the user.
 */
public class ListFloatingCommand extends Command {

    public static final String LIST_COMMAND_WORD = TaskType.SOMEDAY.toString();

    public static final String MESSAGE_SUCCESS = "Listed all " + LIST_COMMAND_WORD + " tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowFloating();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
