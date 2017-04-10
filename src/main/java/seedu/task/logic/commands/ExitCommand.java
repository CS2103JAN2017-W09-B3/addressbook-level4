package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.ExitAppRequestEvent;

//@@author A0146789H
/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String[] COMMAND_WORDS = new String[] {"exit", "quit"};

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting suru, see you next time!";

    /**
     * Constructs a command with the relevant command words.
     */
    public ExitCommand() {
        super(COMMAND_WORDS);
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    //@@author A0146789H
    public static boolean isCommandWord(String command) {
        assert ExitCommand.COMMAND_WORDS != null;

        return isCommandWord(ExitCommand.COMMAND_WORDS, command);
    }
}
