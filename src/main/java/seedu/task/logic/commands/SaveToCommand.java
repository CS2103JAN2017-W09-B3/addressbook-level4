package seedu.task.logic.commands;

import java.util.regex.Pattern;

import com.google.common.eventbus.Subscribe;

import seedu.task.commons.exceptions.DataSavingExceptionEvent;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.ReadOnlyTaskManager;

//@@author A0139938L

public class SaveToCommand extends Command {

    //@@author A0146789H
    public static final String[] COMMAND_WORDS = new String[] {"saveto", "save", "export"};

    public static final String DEFACTO_COMMAND = COMMAND_WORDS[0];

    //@@author A0139938L
    public static final String MESSAGE_USAGE = DEFACTO_COMMAND + ": Defines a new save location for your tasks "
            + "Parameters: FILE_PATH"
            + "Example: " + DEFACTO_COMMAND
            + " ./taskmanager.xml";

    public static final String MESSAGE_SUCCESS = "Your task data has been successfully saved to "
            + "the requested location.";
    private static final String MESSAGE_FAILURE = "There was a problem changing your save location, "
            + "please try again!";
    private static final String VALID_FILE_REGEX = "(.+)?[\\w,\\s,\\d]+\\.xml$";
    private static final Pattern ARGUMENTS_FORMAT = Pattern.compile(VALID_FILE_REGEX);


    private String filepath = "";

    //@@author A0146789H
    protected SaveToCommand() {
        super(COMMAND_WORDS);
    }

    //@@author A0139938L
    public SaveToCommand(String filepath) {
        this();
        this.setFilepath(filepath);
        registerAsAnEventHandler(this);
    }

    /**
     * Executes the command asynchronously
     * */
    public void executeAsync() {
        ReadOnlyTaskManager taskManager = model.getTaskManager();
        model.changeSaveToLocation(taskManager, filepath);
    }

    @Override
    public CommandResult execute() throws CommandException {
        executeAsync();
        return null;
    }

    /**
     *
     * @param dsee
     * @return Command result indicating success or failure of data saving command
     */
    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent dsee) {
        unregisterAsAnEventHandler(this);
        if (dsee.getMessage() == null || dsee.getMessage() == "") {
            writeToChat(MESSAGE_SUCCESS);
        } else {
            writeToChat(MESSAGE_FAILURE + ": " + dsee.toString());
        }
    }

    /**
     * @return the filepath
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * @param filepath the filepath to set
     */
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    //@@author A0146789H
    public static boolean isCommandWord(String command) {
        assert SaveToCommand.COMMAND_WORDS != null;

        return isCommandWord(SaveToCommand.COMMAND_WORDS, command);
    }
}
