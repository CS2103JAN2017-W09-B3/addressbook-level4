package seedu.task.logic.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.eventbus.Subscribe;

import seedu.task.commons.events.storage.DataSavingExceptionEvent;
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

    public static final String MESSAGE_SUCCESS = "Save location has been changed.";
    private static final String MESSAGE_FAILURE = "There was a problem changing your save location";
    private static final String MESSAGE_INVALID_FILE = "Please specify a file name ending in .xml";
    private static final String MESSAGE_ATTEMPT = "Attempting to save file...";
    private static final String VALID_FILE_REGEX = ".+[\\w,\\s,\\d]+\\.xml$";
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
        if (isFilePathValid(filepath)) {
            model.changeSaveToLocation(taskManager, filepath);
        } else {
            writeToChat(MESSAGE_INVALID_FILE);
        }
    }

    /**
     * Checks if file path is a valid xml file.
     * @param filepath
     * @return
     */
    private boolean isFilePathValid(String filepath) {
//        try{
//            Matcher matcher = ARGUMENTS_FORMAT.matcher(filepath);
//            String matched = matcher.matc;
//            return true;
//        }catch(IllegalStateException ise){
//            return false;
//        }
        Matcher matcher = ARGUMENTS_FORMAT.matcher(filepath);
        return matcher.matches();
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
        if (dsee.exception == null) {
            writeToChat(MESSAGE_SUCCESS);
        } else {
            writeToChat(MESSAGE_FAILURE + ": " + dsee.toString());
        }
        unregisterAsAnEventHandler(this);
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
