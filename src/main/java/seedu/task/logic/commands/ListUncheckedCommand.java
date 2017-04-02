//@@author A0139410N
package seedu.task.logic.commands;

/**
 * Lists all incomplete tasks in the task manager to the user.
 */
public class ListUncheckedCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Listed all unchecked tasks";

    //@@author A0146789H
    public static final String[] LIST_COMMAND_WORDS = new String[] {"unchecked"};
    public static final String DEFACTO_COMMAND = LIST_COMMAND_WORDS[0];

    public ListUncheckedCommand() {
        super(LIST_COMMAND_WORDS);
    }

    //@@author A0139410N
    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowUnchecked();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
