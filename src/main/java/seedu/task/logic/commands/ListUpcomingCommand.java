//@@author A0139410N
package seedu.task.logic.commands;

import seedu.task.model.task.CompletionStatus;

/**
 * Lists all upcoming tasks in the task manager to the user.
 */
public class ListUpcomingCommand extends Command {

    public static final String[] LIST_COMMAND_WORDS = new String[] {CompletionStatus.IncompleteType.UPCOMING.toString()};
    public static final String DEFACTO_COMMAND = LIST_COMMAND_WORDS[0];

    public static final String MESSAGE_SUCCESS = "Listed all " + DEFACTO_COMMAND + " tasks";

    public ListUpcomingCommand() {
        super(LIST_COMMAND_WORDS);
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowUpcoming();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public static boolean isCommandWord(String command) {
        assert ListUpcomingCommand.LIST_COMMAND_WORDS != null;

        return isCommandWord(ListUpcomingCommand.LIST_COMMAND_WORDS, command);
    }
}
