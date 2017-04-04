//@@author A0146789H
package seedu.task.logic.commands;

import seedu.task.logic.commands.exceptions.CommandException;

public class ReminderCommand extends Command {
    public static final String[] COMMAND_WORDS = new String[] {"reminders"};
    public static final String DEFACTO_COMMAND = COMMAND_WORDS[0];

    public static final String MESSAGE_ENABLED = "Reminders have been enabled.";
    public static final String MESSAGE_DISABLED = "Reminders have been disabled.";
    public static final String MESSAGE_INCORRECT_ARGS = "Please specify whether to enable or disable reminders!";

    private boolean enabled;

    public ReminderCommand() {
        super(COMMAND_WORDS);
    }
    public ReminderCommand(boolean enabled) {
        this();
        this.enabled = enabled;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (enabled) {
            return new CommandResult(MESSAGE_ENABLED);
        }
        return new CommandResult(MESSAGE_DISABLED);
    }

    public static boolean isCommandWord(String command) {
        return isCommandWord(ReminderCommand.COMMAND_WORDS, command);
    }
}
