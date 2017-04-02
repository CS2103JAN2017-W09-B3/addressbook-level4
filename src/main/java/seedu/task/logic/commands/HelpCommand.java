package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.ShowHelpRequestEvent;

//@@author A0146789H
/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String[] COMMAND_WORDS = new String[] {"help"};
    public static final String DEFACTO_COMMAND = COMMAND_WORDS[0];

    public static final String MESSAGE_USAGE = DEFACTO_COMMAND + ": Shows program usage instructions.\n"
            + "Example: " + DEFACTO_COMMAND;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    /**
     * Constructs a command with the relevant command words.
     */
    public HelpCommand() {
        super(COMMAND_WORDS);
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
}
