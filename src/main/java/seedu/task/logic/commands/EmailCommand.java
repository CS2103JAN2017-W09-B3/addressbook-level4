//@@author A0146789H
package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;

/**
 * @author amon
 *
 */
public class EmailCommand extends Command {
    public static final String[] COMMAND_WORDS = new String[] {"email"};
    public static final String DEFACTO_COMMAND = COMMAND_WORDS[0];

    public static final String MESSAGE_SUCCESS = "The new email has been set successfully!";
    public static final String MESSAGE_INCORRECT = "Please specify a valid email!";

    private String email;

    public EmailCommand() {
        super(COMMAND_WORDS);
    }

    public EmailCommand(String email) {
        this();
        this.email = email;
    }

    /* (non-Javadoc)
     * @see seedu.task.logic.commands.Command#execute()
     */
    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public static boolean isCommandWord(String command) {
        return isCommandWord(EmailCommand.COMMAND_WORDS, command);
    }
}
