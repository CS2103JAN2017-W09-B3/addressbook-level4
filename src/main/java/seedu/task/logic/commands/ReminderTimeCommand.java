//@@author A0146789H
package seedu.task.logic.commands;

import java.io.IOException;
import java.util.Optional;

import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.UserPrefs;

/**
 * @author amon
 *
 */
public class ReminderTimeCommand extends Command {
    public static final String[] COMMAND_WORDS = new String[] {"remindme", "remindtime"};
    public static final String DEFACTO_COMMAND = COMMAND_WORDS[0];

    public static final String MESSAGE_SUCCESS = "New reminder time set successfully! "
            + "Will remind %d minutes before the event!";
    public static final String MESSAGE_INCORRECT = "Please specify a valid integer!";
    public static final String MESSAGE_USERPREFS_ERROR = "There was an error retrieving/saving the preferences file!";

    private int time;

    public ReminderTimeCommand() {
        super(COMMAND_WORDS);
    }

    public ReminderTimeCommand(int time) {
        this();
        this.time = time;
    }

    /* (non-Javadoc)
     * @see seedu.task.logic.commands.Command#execute()
     */
    @Override
    public CommandResult execute() throws CommandException {
        try {
            Optional<UserPrefs> optPrefs = storage.readUserPrefs();
            if (!optPrefs.isPresent()) {
                return new CommandResult(MESSAGE_USERPREFS_ERROR);
            }
            UserPrefs prefs = optPrefs.get();
            prefs.setReminderTime(time);
            storage.saveUserPrefs(prefs);
        } catch (DataConversionException e) {
            return new CommandResult(MESSAGE_USERPREFS_ERROR);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_USERPREFS_ERROR);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, time));
    }

    public static boolean isCommandWord(String command) {
        return isCommandWord(ReminderTimeCommand.COMMAND_WORDS, command);
    }
}
