//@@author A0139410N
package seedu.task.logic.commands;

import seedu.task.model.task.TaskType;

/**
 * Lists all floating tasks in the task manager to the user.
 */
public class ListFloatingCommand extends Command {

    public static final String[] LIST_COMMAND_WORDS = new String[] {TaskType.SOMEDAY.toString(),
                                                                    "floating", "whenever"};
    public static final String DEFACTO_COMMAND = LIST_COMMAND_WORDS[0];

    public static final String MESSAGE_SUCCESS = "All floating tasks has been listed!";

    public ListFloatingCommand() {
        super(LIST_COMMAND_WORDS);
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowFloating();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    public static boolean isCommandWord(String command) {
        assert ListFloatingCommand.LIST_COMMAND_WORDS != null;

        return isCommandWord(ListFloatingCommand.LIST_COMMAND_WORDS, command);
    }
}
