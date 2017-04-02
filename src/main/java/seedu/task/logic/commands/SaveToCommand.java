package seedu.task.logic.commands;

import seedu.task.model.ReadOnlyTaskManager;

//@@author A0139938L

public class SaveToCommand extends Command {

    //@@author A0146789H
    public static final String[] COMMAND_WORDS = new String[] {"saveto"};
    public static final String DEFACTO_COMMAND = COMMAND_WORDS[0];

    //@@author A0139938L
    public static final String MESSAGE_USAGE = DEFACTO_COMMAND + ": Defines a new save location for your tasks "
            + "Parameters: FILE_PATH"
            + "Example: " + DEFACTO_COMMAND
            + " ./taskmanager.xml";

    public static final String MESSAGE_SUCCESS = "Save location has been changed.";

    private String filepath = "";

    //@@author A0146789H
    protected SaveToCommand() {
        super(COMMAND_WORDS);
    }

    //@@author A0139938L
    public SaveToCommand(String filepath){
        this();
        this.setFilepath(filepath);
    }

    @Override
    public CommandResult execute() {
        ReadOnlyTaskManager taskManager = model.getTaskManager();
        model.changeSaveToLocation(taskManager, filepath);
        //TODO: Check if save worked before returning the message.
        return new CommandResult(MESSAGE_SUCCESS);
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

}
