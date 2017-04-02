package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;

//@@author A0146789H
/**
 * Represents an incorrect command. Upon execution, throws a CommandException with feedback to the user.
 */
public class IncorrectCommand extends Command {

    public String feedbackToUser;

    /**
     * Constructs a command with no command words.
     */
    private IncorrectCommand() {
        super(new String[]{});
    }

    public IncorrectCommand(String feedbackToUser) {
        this();
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(feedbackToUser);
    }

}

