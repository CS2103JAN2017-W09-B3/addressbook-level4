//@@author A0139410N
package seedu.task.logic.commands;

import seedu.task.model.task.CompletionStatus;

/**
 * Lists all overdue tasks in the task manager to the user.
 */
public class ListOverdueCommand extends Command {

    public static final String[] LIST_COMMAND_WORDS = new String[] {CompletionStatus.IncompleteType.OVERDUE.toString()};
    public static final String DEFACTO_COMMAND = LIST_COMMAND_WORDS[0];

    public static final String MESSAGE_SUCCESS = "Listed all " + DEFACTO_COMMAND + " tasks";

    public ListOverdueCommand() {
        super(LIST_COMMAND_WORDS);
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowOverdue();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    public static boolean isCommandWord(String command) {
        assert ListOverdueCommand.LIST_COMMAND_WORDS != null;

        return isCommandWord(ListOverdueCommand.LIST_COMMAND_WORDS, command);
    }
}
