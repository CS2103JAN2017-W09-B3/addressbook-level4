//@@author A0139410N
package seedu.task.logic.commands;

import seedu.task.model.task.TaskType;

/**
 * Lists all deadline tasks in the task manager to the user.
 */
public class ListEventCommand extends Command {

    public static final String[] LIST_COMMAND_WORDS = new String[] {TaskType.EVENT.toString()};
    public static final String DEFACTO_COMMAND = LIST_COMMAND_WORDS[0];

    public static final String MESSAGE_SUCCESS = "Listed all " + DEFACTO_COMMAND + " tasks";

    public ListEventCommand() {
        super(LIST_COMMAND_WORDS);
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowEvent();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public static boolean isCommandWord(String command) {
        assert ListEventCommand.LIST_COMMAND_WORDS != null;

        return isCommandWord(ListEventCommand.LIST_COMMAND_WORDS, command);
    }
}
