package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.core.Messages;
import seedu.task.commons.events.BaseEvent;
import seedu.task.commons.events.ui.NewResultAvailableEvent;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.Model;
import seedu.task.storage.Storage;

//@@author A0146789H
/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    protected Storage storage;

    /**
     * Constructs the class with an array of valid command words.
     *
     * @param COMMAND_WORDS
     */
    public Command(String[] commandWords) {
        super();
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
    public void setData(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
    }

    /**
     * Checks if the provided command word belongs to this command.
     *
     * @param commmand
     * @return true if it is a command word, false if not.
     */
    protected static boolean isCommandWord(String[] commandWords, String command) {
        for (String i : commandWords) {
            if (i.equals(command)) {
                return true;
            }
        }
        return false;
    }

    //@@author A0139938L
    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    /**
     * Unregisters the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    protected void unregisterAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().unregisterHandler(handler);
    }

    /**
     * Writes message to chatbox as Suru
     * @param message
     */
    public void writeToChat(String message) {
        raise(new NewResultAvailableEvent(message));
    }
    /**
     * Raises the event via {@link EventsCenter#post(BaseEvent)}
     * @param event
     */
    protected void raise(BaseEvent event) {
        EventsCenter.getInstance().post(event);
    }

    //@@author

}
