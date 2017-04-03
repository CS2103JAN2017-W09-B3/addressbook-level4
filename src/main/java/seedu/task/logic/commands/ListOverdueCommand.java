//@@author A0139410N
package seedu.task.logic.commands;

import seedu.task.model.task.CompletionStatus;

/**
 * Lists all overdue tasks in the task manager to the user.
 */
public class ListOverdueCommand extends Command {

    public static final String LIST_COMMAND_WORD = CompletionStatus.IncompleteType.OVERDUE.toString();

    public static final String MESSAGE_SUCCESS = "Listed all " + LIST_COMMAND_WORD + " tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowOverdue();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
