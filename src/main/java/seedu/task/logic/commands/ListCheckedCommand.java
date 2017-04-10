//@@author A0139410N
package seedu.task.logic.commands;

/**
 * Lists all completed tasks in the task manager to the user.
 */
public class ListCheckedCommand extends Command {

    public static final String MESSAGE_SUCCESS = "All completed tasks has been listed!";

    //@@author A0146789H
    public static final String[] LIST_COMMAND_WORDS = new String[] {"checked", "completed"};
    public static final String DEFACTO_COMMAND = LIST_COMMAND_WORDS[0];

    public ListCheckedCommand() {
        super(LIST_COMMAND_WORDS);
    }

    //@@author A0139410N
    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowChecked();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    //@@author A0146789H
    public static boolean isCommandWord(String command) {
        assert ListCheckedCommand.LIST_COMMAND_WORDS != null;

        return isCommandWord(ListCheckedCommand.LIST_COMMAND_WORDS, command);
    }
}
