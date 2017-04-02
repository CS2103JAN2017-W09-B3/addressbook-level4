package seedu.task.logic.commands;

import seedu.task.model.ReadOnlyTaskManager;

//@@author A0139938L

public class SaveToCommand extends Command {

    public static final String COMMAND_WORD = "saveto";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Defines a new save location for your tasks "
            + "Parameters: FILE_PATH"
            + "Example: " + COMMAND_WORD
            + " ./taskmanager.xml";

    public static final String MESSAGE_SUCCESS = "Save location has been changed.";

    private String filepath = "";

    public SaveToCommand(String filepath) {
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
