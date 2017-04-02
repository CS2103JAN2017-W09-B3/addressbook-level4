package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.Model;

//@@author A0146789H
/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    private final String[] commandWords;

    /**
     * Constructs the class with an array of valid command words.
     *
     * @param cOMMAND_WORDS
     */
    public Command(String[] commandWords) {
        super();
        this.commandWords = commandWords;
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model) {
        this.model = model;
    }

    /**
     * Checks if the provided command word belongs to this command.
     *
     * @param commmand
     * @return true if it is a command word, false if not.
     */
    public boolean isCommandWord(String command) {
        for (String i : this.commandWords) {
            if (i.equals(command)) {
                return true;
            }
        }
        return false;
    }

}
