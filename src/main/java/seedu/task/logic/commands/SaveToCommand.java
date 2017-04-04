package seedu.task.logic.commands;

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
    private static final String MESSAGE_ATTEMPT = "Attempting to save file...";

    private String filepath = "";

    //@@author A0146789H
    protected SaveToCommand() {
        super(COMMAND_WORDS);
        registerAsAnEventHandler(this);
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
        writeToChat(MESSAGE_ATTEMPT);
    }

    @Override
    public CommandResult execute() throws CommandException {
        executeAsync();
        return new CommandResult("Hi");
    }

    /**
     *
     * @param dsee
     * @return Command result indicating success or failure of data saving command
     */
    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent dsee) {
        if(dsee.exception==null){
            writeToChat(MESSAGE_SUCCESS);
        }else{
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
