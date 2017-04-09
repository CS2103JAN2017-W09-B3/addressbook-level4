//@@author A0146789H
package seedu.task.logic.commands;

import java.io.IOException;
import java.util.Optional;

import seedu.task.commons.core.Config;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.commons.util.HttpUtil;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.UserPrefs;

public class ReminderCommand extends Command {
    public static final String[] COMMAND_WORDS = new String[] {"reminders"};
    public static final String DEFACTO_COMMAND = COMMAND_WORDS[0];

    public static final String MESSAGE_ENABLED = "Reminders have been enabled and will be synced to the cloud.";
    public static final String MESSAGE_DISABLED = "Reminders have been disabled.";
    public static final String MESSAGE_INCORRECT_ARGS = "Please specify whether to enable or disable reminders!";
    public static final String MESSAGE_USERPREFS_ERROR = "There was an error retrieving the configuration files!";
    public static final String MESSAGE_EMAILNOTSET_ERROR = "There was no email previously set! Use the 'email' "
            + "command!";
    public static final String MESSAGE_SYNCFAILURE_ERROR = "There was an error syncing with the server!";

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
        Optional<UserPrefs> optPrefs = null;
        Optional<Config> optConfig = null;
        try {
            optPrefs = storage.readUserPrefs();
            optConfig = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE);
            if (!optPrefs.isPresent() || !optConfig.isPresent()) {
                return new CommandResult(MESSAGE_USERPREFS_ERROR);
            }
        } catch (DataConversionException e) {
            return new CommandResult(MESSAGE_USERPREFS_ERROR);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_USERPREFS_ERROR);
        }

        UserPrefs prefs = optPrefs.get();
        Config config = optConfig.get();

        if (prefs == null) {
            return new CommandResult(MESSAGE_USERPREFS_ERROR);
        }

        if (prefs.reminderEmail.equals("")) {
            return new CommandResult(MESSAGE_EMAILNOTSET_ERROR);
        }

        // Try to push the file to the server. If it works, then return an affirmative result
        if (enabled && HttpUtil.pushSaveFile(prefs.reminderEmail, prefs.reminderTime,
                config.getTaskManagerFilePath())) {
            return new CommandResult(MESSAGE_ENABLED);
        } else if (!enabled && HttpUtil.pushDisable(prefs.reminderEmail)) {
            return new CommandResult(MESSAGE_DISABLED);
        }

        return new CommandResult(MESSAGE_SYNCFAILURE_ERROR);
    }

    public static boolean isCommandWord(String command) {
        return isCommandWord(ReminderCommand.COMMAND_WORDS, command);
    }
}
