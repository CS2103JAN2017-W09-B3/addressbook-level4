package seedu.task.logic.commands;

import com.google.common.eventbus.Subscribe;

import seedu.task.commons.exceptions.DataLoadingExceptionEvent;

//@@author A0139938L

public class LoadFromCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Your requested task data has been successfully loaded!";
    private static final String MESSAGE_FAILURE = "There was a problem changing your load location, "
            + "please try again.";

    private String filepath = "";
    //@@author A0146789H

    public static final String[] COMMAND_WORDS = new String[] {"loadfrom", "load", "import"};

    public static final String DEFACTO_COMMAND = COMMAND_WORDS[0];

    public static final String MESSAGE_USAGE = DEFACTO_COMMAND + ": Defines a new load location for your tasks "
            + "Parameters: FILE_PATH"
            + "Example: " + DEFACTO_COMMAND
            + " ./taskmanager.xml";


    private LoadFromCommand() {
        super(COMMAND_WORDS);
    }

    public LoadFromCommand(String filepath) {
        this();
        this.setFilepath(filepath);
        registerAsAnEventHandler(this);
    }

    //@@author A0139938L
    @Override
    public CommandResult execute() {
        executeAsync();
        return null;
    }

    private void executeAsync() {
        model.changeLoadFromLocation(filepath);
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
    /**
     *
     * @param dlee
     * @return Command result indicating success or failure of data loading command
     */
    @Subscribe
    private void handleDataLoadingExceptionEvent(DataLoadingExceptionEvent dlee) {
        unregisterAsAnEventHandler(this);
        if (dlee.getMessage() == null || dlee.getMessage() == "") {
            writeToChat(MESSAGE_SUCCESS);
        } else {
            writeToChat(MESSAGE_FAILURE + ": " + dlee.toString());
        }
    }

    //@@author A0146789H
    public static boolean isCommandWord(String command) {
        assert LoadFromCommand.COMMAND_WORDS != null;

        return isCommandWord(LoadFromCommand.COMMAND_WORDS, command);
    }
}
