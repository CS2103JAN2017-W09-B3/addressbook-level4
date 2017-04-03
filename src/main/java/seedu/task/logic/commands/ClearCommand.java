package seedu.task.logic.commands;

import seedu.task.model.TaskManager;

//@@author A0146789H
/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String[] COMMAND_WORDS = new String[] {"clear"};
    public static final String MESSAGE_SUCCESS = "Task manager has been cleared!";

    /**
     * Creates and empty command with the relevant command words.
     */
    public ClearCommand() {
        super(COMMAND_WORDS);
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    //@@author A0146789H
    public static boolean isCommandWord(String command) {
        assert ClearCommand.COMMAND_WORDS != null;

        return isCommandWord(ClearCommand.COMMAND_WORDS, command);
    }
}
